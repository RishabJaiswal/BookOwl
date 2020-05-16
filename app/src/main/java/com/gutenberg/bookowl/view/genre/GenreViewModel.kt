package com.gutenberg.bookowl.view.genre

import android.content.res.TypedArray
import androidx.lifecycle.ViewModel

class GenreViewModel : ViewModel() {

    /**
     * In an ideal scenario "Genre list" is created
     * from an API response
     * */
    fun getGenreList(icons: TypedArray, titles: Array<String>): List<Genre> {
        val genreList = ArrayList<Genre>(titles.size)

        //creating custom list
        titles.forEachIndexed { index, title ->
            genreList.add(Genre(icons.getResourceId(index, 0), title))
        }
        icons.recycle()
        return genreList
    }
}