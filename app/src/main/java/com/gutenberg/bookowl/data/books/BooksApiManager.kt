package com.gutenberg.bookowl.data.books

import com.gutenberg.bookowl.data.ApiClient
import com.gutenberg.bookowl.data.models.Book
import io.reactivex.Flowable

class BooksApiManager {

    val booksApiService = ApiClient.createApiService(BooksApiService::class.java)

    fun getBooks(genreTitle: String): Flowable<List<Book>> {
        return booksApiService.getBooks(genreTitle)
    }
}