package com.gutenberg.bookowl.data.books

import com.gutenberg.bookowl.application.MIME_IMAGE
import com.gutenberg.bookowl.data.ApiClient
import com.gutenberg.bookowl.data.models.BookSearchResult
import io.reactivex.Flowable

class BooksApiManager {

    private val booksApiService = ApiClient.createApiService(BooksApiService::class.java)

    fun getBooks(genreTitle: String): Flowable<BookSearchResult> {
        return booksApiService.getBooks(genreTitle, MIME_IMAGE)
    }
}