package com.gutenberg.bookowl.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.gutenberg.bookowl.application.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class BookFormat(

    @JsonProperty(MIME_IMAGE)
    var bookCoverUrl: String? = "",

    @JsonProperty(MIME_PDF)
    var pdfUrl: String? = "",

    @JsonProperty(MIME_TEXT_PLAIN_ASCII)
    var plainTextUrl: String? = "",

    @JsonProperty(MIME_TEXT_PLAIN_UTF_8)
    var plainText_UTF_8_Url: String? = "",

    @JsonProperty(MIME_TEXT_HTML_ASCII)
    var htmlTextUrl: String? = null,

    @JsonProperty(MIME_TEXT_HTML_UTF_8)
    var htmlText_UTF_8_Url: String? = null

) /*{

    @JsonSetter(MIME_TEXT_PLAIN_UTF_8)
    fun setPlainTextUtf8(value: String){
        plainTextUrl = value
    }

    @JsonSetter(MIME_TEXT_HTML_UTF_8)
    fun setHtmlTextUtf8(value: String) {
        htmlTextUrl = value
    }
}*/