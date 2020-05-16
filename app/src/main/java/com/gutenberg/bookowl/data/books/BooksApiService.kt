package com.gutenberg.bookowl.data.books

import com.gutenberg.bookowl.data.models.Book
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApiService {

    @GET("books/")
    fun getBooks(
        @Query("topic") genre: String
    ): Flowable<List<Book>>

    @GET("books/")
    fun getBooks(
        @Query("topic") genre: String,
        @Query("search") searchString: String
    ): Flowable<List<Book>>
}