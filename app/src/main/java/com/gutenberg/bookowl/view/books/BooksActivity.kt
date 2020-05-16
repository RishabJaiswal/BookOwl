package com.gutenberg.bookowl.view.books

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gutenberg.bookowl.R
import com.gutenberg.bookowl.application.KEY_GENRE
import com.gutenberg.bookowl.application.extensions.configureViewModel
import kotlinx.android.synthetic.main.activity_books.*

class BooksActivity : AppCompatActivity() {

    private val viewModel by lazy {
        val genreTitle = intent.getStringExtra(KEY_GENRE)
        configureViewModel {
            BooksViewModel(genreTitle ?: "")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)
        tv_genre_title.text = viewModel.genreTitle.capitalize()
    }

    companion object {
        fun getIntent(context: Context, genreTitle: String): Intent {
            return Intent(context, BooksActivity::class.java).apply {
                putExtra(KEY_GENRE, genreTitle)
            }
        }
    }
}
