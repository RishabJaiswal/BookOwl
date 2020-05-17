package com.gutenberg.bookowl.application.extensions

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.subscribeOnBackObserverOnMain(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.subscribeOnBackObserverOnMain(): Single<T> {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}


fun Disposable.addTo(disposable: CompositeDisposable) {
    disposable.add(this)
}
