package com.gutenberg.bookowl.data.books

import com.gutenberg.bookowl.data.models.BookSearchResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface BooksResultPageApiService {
    @GET
    fun getBooksFromPage(
        @Url pagePath: String
    ): Single<BookSearchResult>
}