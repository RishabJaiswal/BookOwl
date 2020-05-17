package com.gutenberg.bookowl.view

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity


abstract class BaseActivity : AppCompatActivity() {

    fun toast(@StringRes message: Int) {
        createToast(getString(message)).show()
    }

    fun toast(message: String) {
        createToast(message).show()
    }

    fun createToast(message: String): Toast {
        return Toast.makeText(this, message, Toast.LENGTH_LONG)
    }
}