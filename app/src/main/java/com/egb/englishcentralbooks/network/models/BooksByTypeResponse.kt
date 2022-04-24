package com.egb.englishcentralbooks.network.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BooksByTypeResponse(
    @SerialName("results")
    val results: Results?,
)