package com.gutenberg.bookowl.data.books

import com.gutenberg.bookowl.data.models.BookSearchResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApiService {

    @GET("books/")
    fun getBooks(
        @Query("topic") genre: String,
        @Query("mime_type") mimeType: String
    ): Single<BookSearchResult>

    @GET("books/")
    fun getBooks(
        @Query("topic") genre: String,
        @Query("search") searchString: String,
        @Query("mime_type") mimeType: String
    ): Single<BookSearchResult>
}