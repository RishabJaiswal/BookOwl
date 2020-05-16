package com.gutenberg.bookowl.data.models

import com.fasterxml.jackson.annotation.JsonProperty

data class BookSearchResult(

    @JsonProperty("count")
    var totalBooks: Int = 0,

    @JsonProperty("next")
    var nextPageUrl: String? = null,

    @JsonProperty("previous")
    var previousPageUrl: String? = null,

    @JsonProperty("results")
    var booksList: List<Book> = emptyList()
)