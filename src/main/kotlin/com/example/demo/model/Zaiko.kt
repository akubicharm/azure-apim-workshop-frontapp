package com.example.demo.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Zaiko(
    @JsonProperty("name") val name: String,
    @JsonProperty("qty") val qty: Int,
    @JsonProperty("unitprice") val unitprice: Int,
)