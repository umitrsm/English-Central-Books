package com.egb.englishcentralbooks.network.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Book(
    @SerialName("age_group")
    val ageGroup: String = "",
    @SerialName("amazon_product_url")
    val amazonProductUrl: String = "",
    @SerialName("author")
    val author: String = "",
    @SerialName("book_image")
    val bookImage: String = "",
    @SerialName("book_image_height")
    val bookImageHeight: Int = 0,
    @SerialName("book_image_width")
    val bookImageWidth: Int = 0,
    @SerialName("contributor")
    val contributor: String = "",
    @SerialName("description")
    val description: String = "",
    @SerialName("price")
    val price: String = "",
    @SerialName("publisher")
    val publisher: String?,
    @SerialName("title")
    val title: String = "",
    @SerialName("type")
    var type: String = "",
)
