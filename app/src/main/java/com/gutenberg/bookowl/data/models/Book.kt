package com.gutenberg.bookowl.data.models

import com.fasterxml.jackson.annotation.JsonProperty

data class Book(

    @JsonProperty("title")
    var title: String = "",

    @JsonProperty("authors")
    var authors: List<Author> = emptyList(),

    @JsonProperty("formats")
    var bookFormat: BookFormat = BookFormat()
)