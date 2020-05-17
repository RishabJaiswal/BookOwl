package com.gutenberg.bookowl.view.books

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gutenberg.bookowl.R
import com.gutenberg.bookowl.application.KEY_GENRE
import com.gutenberg.bookowl.application.extensions.*
import com.gutenberg.bookowl.application.utils.GridSpacingItemDecoration
import com.gutenberg.bookowl.data.models.Book
import com.gutenberg.bookowl.view.BaseActivity
import kotlinx.android.synthetic.main.activity_books.*
import kotlin.math.roundToInt


class BooksActivity : BaseActivity(), View.OnClickListener {

    private var areBooksScrolledToLast: Boolean = false
    private lateinit var booksScrollListener: RecyclerView.OnScrollListener
    private val booksAdapter = BooksAdapter(this::onBookClicked)
    private val viewModel by lazy {
        val genreTitle = intent.getStringExtra(KEY_GENRE)
        configureViewModel {
            BooksViewModel(genreTitle ?: "")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)
        btn_back.setOnClickListener(this)

        //setting genre and books adapter
        tv_genre_title.text = viewModel.genreTitle.capitalize()

        //setting up books listing
        initBooksListing()
        observeBooksList()
        initSearch()

        //fetching initial books list
        viewModel.getBooks()
    }

    override fun onDestroy() {
        rv_books.removeOnScrollListener(booksScrollListener)
        super.onDestroy()
    }

    /**
     * responding to changes in books list query
     * */
    private fun observeBooksList() {
        viewModel.booksLiveResult.observe(this, Observer {
            it.parseResult({
                //loading
                toggleProgress(show = true)
            }, { booksList ->
                //content
                toggleProgress(show = false)
                booksAdapter.swapData(booksList)
            }, { errorThrowable ->
                //error
                showError(errorThrowable)
                logError(
                    tag = "BooksError",
                    message = "Error getting books",
                    throwable = errorThrowable
                )
            })
        })
    }


    /**
     * Configures recyclerView for books
     * */
    private fun initBooksListing() {

        //setting up grid span count based on screen size
        val display = windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)

        val density = resources.displayMetrics.density
        val screenDpWidth = outMetrics.widthPixels / density
        val columns = (screenDpWidth / 114).roundToInt() - 1
        val layoutManager = GridLayoutManager(this, columns)
        rv_books.layoutManager = layoutManager

        //adding equal spacing item decoration
        rv_books.addItemDecoration(
            GridSpacingItemDecoration(
                columns,
                resources.getDimensionPixelSize(R.dimen.width_book_cover)
            )
        )


        //setting up scroll listener
        booksScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                //calculating if the user has scrolled to last items
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                areBooksScrolledToLast = firstVisibleItem + visibleItemCount >= totalItemCount

                //loading more books
                if (viewModel.canLoadMoreBooks() && areBooksScrolledToLast) {
                    viewModel.getBooksFromNextPage()
                }
            }
        }

        rv_books.apply {
            adapter = booksAdapter
            addOnScrollListener(booksScrollListener)
        }
    }


    /**
     * Handle state for views related to error
     * */
    private fun showError(throwable: Throwable) {
        toggleProgress(show = false)
        imv_error.visibleOrGone(booksAdapter.itemCount == 0)

        //showing error image
        if (booksAdapter.itemCount == 0) {
            imv_error.setImageResource(
                if (throwable.causedByInternetConnectionIssue())
                    R.drawable.art_no_internet
                else
                    R.drawable.art_something_wrong
            )
        }

        //showing toast
        toast(
            if (throwable.causedByInternetConnectionIssue())
                R.string.error_no_internet
            else
                R.string.error_unknown
        )
    }

    /**
     * Handle state for views related to loading
     * */
    private fun toggleProgress(show: Boolean) {
        pb_loading.visibleOrGone(show && areBooksScrolledToLast.not())
        pb_load_more_books.visibleOrGone(show && areBooksScrolledToLast)
    }

    /**
     * Start listening to user search
     * */
    private fun initSearch() {
        sv_search_books.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchQuerySubject.onComplete()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchQuerySubject.onNext(newText ?: "")
                return true
            }
        })
        viewModel.listenToSearch()
    }


    private fun onBookClicked(book: Book) {
        book.bookFormat.apply {
            val allFormats = arrayOf<String?>(
                htmlText_UTF_8_Url, htmlTextUrl,
                pdfUrl,
                plainText_UTF_8_Url, plainTextUrl
            )

            allFormats.forEach { formatUrl ->
                if (formatUrl.isNullOrEmpty().not()) {
                    formatUrl?.openLink(this@BooksActivity)
                    return@forEach
                }
            }
        }
    }

    //click listener
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_back -> onBackPressed()
        }
    }

    companion object {
        fun getIntent(context: Context, genreTitle: String): Intent {
            return Intent(context, BooksActivity::class.java).apply {
                putExtra(KEY_GENRE, genreTitle)
            }
        }
    }
}
