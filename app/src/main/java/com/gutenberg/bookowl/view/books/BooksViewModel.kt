package com.gutenberg.bookowl.view.books

import androidx.lifecycle.ViewModel
import com.gutenberg.bookowl.application.LiveResult
import com.gutenberg.bookowl.application.extensions.addTo
import com.gutenberg.bookowl.application.extensions.subscribeOnBackObserverOnMain
import com.gutenberg.bookowl.data.books.BooksApiManager
import com.gutenberg.bookowl.data.models.Book
import io.reactivex.disposables.CompositeDisposable

class BooksViewModel(val genreTitle: String) : ViewModel() {

    private val booksApiManager = BooksApiManager()
    val booksLiveResult = LiveResult<List<Book>>()
    private val disposable = CompositeDisposable()

    fun getBooks() {
        booksApiManager.getBooks(genreTitle)
            .subscribeOnBackObserverOnMain()
            .subscribe({ bookSearchResult ->
                //success
                booksLiveResult.success(bookSearchResult.booksList)
            }, { throwable ->
                //error
                booksLiveResult.error(throwable)
            })
            .addTo(disposable)
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}