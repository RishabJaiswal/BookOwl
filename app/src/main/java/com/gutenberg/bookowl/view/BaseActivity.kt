package com.gutenberg.bookowl.view

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


abstract class BaseActivity : AppCompatActivity() {

    private var baseAlertDialog: AlertDialog? = null

    fun toast(@StringRes message: Int) {
        createToast(getString(message)).show()
    }

    fun toast(message: String) {
        createToast(message).show()
    }

    fun createToast(message: String): Toast {
        return Toast.makeText(this, message, Toast.LENGTH_LONG)
    }

    //method to show error dialog
    protected fun showAlertDialog(
        @StringRes title: Int,
        @StringRes message: Int,
        @StringRes positiveLabel: Int,
        @StringRes negativeLabel: Int? = null,
        onPositiveAction: (() -> Unit)? = null,
        onNegativeAction: (() -> Unit)? = null
    ) {
        if (baseAlertDialog == null) {
            val dialogBuilder = AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveLabel) { dialog, _ ->
                    onPositiveAction?.invoke() ?: dialog.dismiss()
                }
            negativeLabel?.let {
                dialogBuilder.setNegativeButton(it) { dialog, _ ->
                    onNegativeAction?.invoke() ?: dialog.dismiss()
                }
            }
            baseAlertDialog = dialogBuilder.create()
        } else {
            baseAlertDialog?.apply {
                dismiss()
                setTitle(title)
                setMessage(getString(message))

                //setting positive button
                setButton(
                    AlertDialog.BUTTON_POSITIVE,
                    getString(positiveLabel)
                ) { _, _ ->
                    onPositiveAction?.invoke()
                }

                //setting negative button
                val negativeLblString = negativeLabel?.let {
                    getString(it)
                } ?: ""

                setButton(
                    AlertDialog.BUTTON_NEGATIVE,
                    negativeLblString
                ) { _, _ ->
                    onNegativeAction?.invoke()
                }
            }
        }
        baseAlertDialog?.show()
    }
}