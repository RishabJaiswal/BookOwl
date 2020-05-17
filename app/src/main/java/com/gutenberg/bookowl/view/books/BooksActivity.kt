package com.gutenberg.bookowl.view.books

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.lifecycle.Observer
import com.gutenberg.bookowl.R
import com.gutenberg.bookowl.application.KEY_GENRE
import com.gutenberg.bookowl.application.extensions.*
import com.gutenberg.bookowl.view.BaseActivity
import kotlinx.android.synthetic.main.activity_books.*

class BooksActivity : BaseActivity() {

    private val booksAdapter = BooksAdapter()
    private val viewModel by lazy {
        val genreTitle = intent.getStringExtra(KEY_GENRE)
        configureViewModel {
            BooksViewModel(genreTitle ?: "")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        //setting genre and books adapter
        tv_genre_title.text = viewModel.genreTitle.capitalize()
        rv_books.adapter = booksAdapter
        observeBooksList()
        initSearch()

        //fetching initial books list
        viewModel.getBooks()
    }

    //responding to changes in books list query
    private fun observeBooksList() {
        viewModel.booksLiveResult.observe(this, Observer {
            it.parseResult({
                //loading
                pb_loading.visible()
            }, { booksList ->
                //content
                pb_loading.gone()
                booksAdapter.swapData(booksList)
            }, { errorThrowable ->
                //error
                showError(errorThrowable)
            })
        })
    }


    private fun showError(throwable: Throwable) {
        pb_loading.gone()
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

    companion object {
        fun getIntent(context: Context, genreTitle: String): Intent {
            return Intent(context, BooksActivity::class.java).apply {
                putExtra(KEY_GENRE, genreTitle)
            }
        }
    }
}
