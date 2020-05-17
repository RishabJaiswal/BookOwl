package com.gutenberg.bookowl.view.books

import androidx.lifecycle.ViewModel
import com.gutenberg.bookowl.application.LiveResult
import com.gutenberg.bookowl.application.extensions.addTo
import com.gutenberg.bookowl.application.extensions.subscribeOnBackObserverOnMain
import com.gutenberg.bookowl.data.books.BooksApiManager
import com.gutenberg.bookowl.data.models.Book
import com.gutenberg.bookowl.data.models.BookSearchResult
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class BooksViewModel(val genreTitle: String) : ViewModel() {

    //connects to the api endpoint
    private val booksApiManager = BooksApiManager()
    //reactive stream for books list
    val booksLiveResult = LiveResult<MutableList<Book>>()
    //search string reactive stream
    val searchQuerySubject: PublishSubject<String> by lazy {
        PublishSubject.create<String>()
    }
    //stores the latest result for books
    var latestBookSearchResult: BookSearchResult? = null
    private val disposable = CompositeDisposable()

    fun getBooks() {
        booksApiManager.getBooks(genreTitle)
            .subscribeOnBackObserverOnMain()
            .doOnSubscribe { booksLiveResult.loading() }
            .subscribe({ bookSearchResult ->
                //success
                latestBookSearchResult = bookSearchResult
                booksLiveResult.success(bookSearchResult.booksList.toMutableList())
            }, { throwable ->
                //error
                booksLiveResult.error(throwable)
            })
            .addTo(disposable)
    }

    /**
     * Fetches next page from the current result of books
     * */
    fun getBooksFromNextPage() {
        latestBookSearchResult?.nextPageUrl?.let { nextPageUrl ->
            booksApiManager.getBooksFromPage(nextPageUrl)
                .subscribeOnBackObserverOnMain()
                .doOnSubscribe { booksLiveResult.loading() }
                .subscribe({ nextPageResult ->
                    //success
                    //appending next page books into current books list
                    val booksList = mutableListOf<Book>().apply {
                        addAll(latestBookSearchResult?.booksList ?: emptyList())
                        addAll(nextPageResult.booksList)
                    }
                    booksLiveResult.success(booksList)

                    latestBookSearchResult = nextPageResult
                }, { throwable ->
                    //error
                    booksLiveResult.error(throwable)
                })
                .addTo(disposable)
        }
    }

    /**
     * This method listens for any search queries from the user
     * */
    fun listenToSearch() {
        searchQuerySubject.debounce(400, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap { searchQuery ->

                //defining the observable to return
                val bookQueryObservable: Single<BookSearchResult> =
                    if (searchQuery.isEmpty()) {
                        booksApiManager.getBooks(genreTitle)
                    } else {
                        booksApiManager.getBooks(genreTitle, searchQuery)
                    }

                //returning the observable
                return@switchMap bookQueryObservable
                    .toObservable()
                    .doOnError {
                        booksLiveResult.error(it)
                    }
            }
            .subscribeOnBackObserverOnMain()
            .doOnSubscribe { booksLiveResult.loading() }
            .subscribe({ bookSearchResult ->
                //success
                latestBookSearchResult = bookSearchResult
                booksLiveResult.success(bookSearchResult.booksList.toMutableList())
            }, { throwable ->
                //error
                booksLiveResult.error(throwable)
            })
            .addTo(disposable)
    }


    //checks for availability of next page
    fun canLoadMoreBooks() = latestBookSearchResult?.nextPageUrl != null

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}