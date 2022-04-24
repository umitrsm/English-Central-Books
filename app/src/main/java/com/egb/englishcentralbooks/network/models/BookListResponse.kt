package com.egb.englishcentralbooks.network.models

import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class BookListResponse(
    @SerialName("status")
    val status: String,
    @SerialName("copyright")
    val copyright: String?,
    @SerialName("num_results")
    val num_results: Int?,
    @SerialName("results")
    val results: List<BookList>
)

@Serializable
data class BookList(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = 0,
    @SerialName("list_name")
    val list_name: String = "",
    @SerialName("display_name")
    val display_name: String = "",
    @SerialName("list_name_encoded")
    val list_name_encoded: String = "",
    @SerialName("oldest_published_date")
    val oldest_published_date: String = "",
    @SerialName("newest_published_date")
    val newest_published_date: String = "",
    @SerialName("updated")
    val updated: String = "",
)




