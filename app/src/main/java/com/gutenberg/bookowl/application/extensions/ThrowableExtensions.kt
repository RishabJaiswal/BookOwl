package com.gutenberg.bookowl.application.extensions

import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun <T : Throwable> T.causedByInternetConnectionIssue(): Boolean {
    return (this is UnknownHostException || this is SocketTimeoutException
            || this is ConnectException || this is SocketException
            || (this.cause != null && this.cause!!.causedByInternetConnectionIssue()))
}