package com.egb.englishcentralbooks.network.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Results(
    @SerialName("books")
    val books: List<Book>?,
)