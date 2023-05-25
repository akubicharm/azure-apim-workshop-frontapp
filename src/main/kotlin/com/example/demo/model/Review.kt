package com.example.demo.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Review(
    @JsonProperty("name") val name: String,
    @JsonProperty("rate") val rate: Int,
)