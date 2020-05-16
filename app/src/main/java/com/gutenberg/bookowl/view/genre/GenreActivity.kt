package com.gutenberg.bookowl.view.genre

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gutenberg.bookowl.R
import com.gutenberg.bookowl.application.extensions.configureViewModel
import com.gutenberg.bookowl.data.models.Genre
import com.gutenberg.bookowl.view.books.BooksActivity
import kotlinx.android.synthetic.main.activity_genre.*

class GenreActivity : AppCompatActivity() {

    private val viewModel by lazy {
        configureViewModel<GenreViewModel>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre)
        initGenreList()
    }

    private fun initGenreList() {
        resources.apply {
            val genreList = viewModel.getGenreList(
                obtainTypedArray(R.array.genre_icons),
                getStringArray(R.array.genre_titles)
            )
            rv_genre_listing.adapter =
                GenreListAdapter(genreList, this@GenreActivity::onGenreSelected)
        }
    }

    private fun onGenreSelected(genre: Genre) {
        startActivity(BooksActivity.getIntent(this, genre.title))
    }
}
