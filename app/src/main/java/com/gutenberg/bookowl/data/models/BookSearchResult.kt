package com.gutenberg.bookowl.data.models

import com.fasterxml.jackson.annotation.JsonProperty

class BookSearchResult {

    @JsonProperty("count")
    var totalBooks = 0

    @JsonProperty("next")
    var nextPageUrl: String? = null

    @JsonProperty("previous")
    var previousPageUrl: String? = null

    @JsonProperty("results")
    val booksList: List<Book> = emptyList()
}