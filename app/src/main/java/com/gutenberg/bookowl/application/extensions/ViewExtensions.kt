package com.gutenberg.bookowl.application.extensions

import android.view.View

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.isVisible(): Boolean {
    return this.visibility == View.VISIBLE
}

fun View.isGone(): Boolean {
    return this.visibility == View.GONE
}

fun View.isInvisible(): Boolean {
    return this.visibility == View.INVISIBLE
}

fun View.visibleOrGone(isViewVisible: Boolean) {
    if (isViewVisible) {
        this.visible()
    } else {
        this.gone()
    }
}

fun View.visibleOrInvisible(visible: Boolean) {
    if (visible) {
        this.visible()
    } else {
        this.invisible()
    }
}

