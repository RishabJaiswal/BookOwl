package com.gutenberg.bookowl.view.books

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.gutenberg.bookowl.R
import com.gutenberg.bookowl.application.KEY_GENRE
import com.gutenberg.bookowl.application.extensions.configureViewModel
import kotlinx.android.synthetic.main.activity_books.*

class BooksActivity : AppCompatActivity() {

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
            }, { booksList ->
                //content
                booksAdapter.swapData(booksList)
            }, {
                //error
            })
        })
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
