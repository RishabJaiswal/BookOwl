package com.gutenberg.bookowl.application.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

@Suppress("UNCHECKED_CAST")
class BaseVMFactory<T>(val block: () -> T) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return block() as T
    }
}

inline fun <reified T : ViewModel> FragmentActivity.configureViewModel(noinline block: (() -> T)? = null): T {
    if (block == null) {
        return ViewModelProviders.of(this).get(T::class.java)
    } else {
        return ViewModelProviders.of(
            this,
            BaseVMFactory(block)
        ).get(T::class.java)
    }
}

inline fun <reified T : ViewModel> Fragment.configureViewModel(noinline block: (() -> T)? = null): T {
    if (block == null) {
        return ViewModelProviders.of(this).get(T::class.java)
    } else {
        return ViewModelProviders.of(
            this,
            BaseVMFactory(block)
        ).get(T::class.java)
    }
}