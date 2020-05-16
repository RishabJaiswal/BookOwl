package com.gutenberg.bookowl.data.models

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonProperty
import com.gutenberg.bookowl.application.*

data class BookFormat(

    @JsonProperty(MIME_IMAGE)
    var bookCoverUrl: String? = "",

    @JsonProperty(MIME_PDF)
    var pdfUrl: String? = "",

    @JsonProperty(MIME_TEXT_PLAIN_ASCII)
    @JsonAlias(MIME_TEXT_PLAIN_UTF_8)
    var plainTextUrl: String? = "",

    @JsonProperty(MIME_TEXT_HTML_ASCII)
    @JsonAlias(MIME_TEXT_HTML_UTF_8)
    var htmlTextUrl: String? = null
)