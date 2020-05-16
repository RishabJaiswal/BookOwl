package com.gutenberg.bookowl.data.models

import androidx.annotation.DrawableRes

data class Genre(

    @DrawableRes
    val icon: Int,

    val title: String
)