package com.gutenberg.bookowl.data.models

import com.fasterxml.jackson.annotation.JsonProperty

data class Author(

    @JsonProperty("name")
    var name: String = ""
)