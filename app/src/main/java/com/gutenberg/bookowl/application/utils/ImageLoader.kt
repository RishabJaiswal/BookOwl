package com.gutenberg.bookowl.application.utils

import android.widget.ImageView
import com.gutenberg.bookowl.R
import com.squareup.picasso.Picasso


class ImageLoader {

    companion object {

        const val placeHolderId = R.drawable.shape_rect_grey_cr_8

        //fixme:: add application context instad of viewContext
        fun loadUrl(imageView: ImageView, url: String?) {
            if (!url.isNullOrEmpty()) {
                Picasso.Builder(imageView.context)
                    .build()
                    .load(url)
                    .placeholder(placeHolderId)
                    .into(imageView)
            } else {
                Picasso.Builder(imageView.context)
                    .build()
                    .load(placeHolderId)
                    .into(imageView)
            }
        }

    }
}