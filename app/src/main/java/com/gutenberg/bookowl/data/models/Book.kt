package com.gutenberg.bookowl.data.models

import com.fasterxml.jackson.annotation.JsonProperty

class Book {

    @JsonProperty("title")
    var title = ""

    @JsonProperty("authors")
    var authors = emptyList<Author>()
}