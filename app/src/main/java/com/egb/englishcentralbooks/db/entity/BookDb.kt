package com.egb.englishcentralbooks.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookDb(
    @ColumnInfo(name = "description")
    val description: String = "",
    @ColumnInfo(name = "price")
    val price: String = "",
    @ColumnInfo(name = "title")
    @PrimaryKey
    val title: String = "",
    @ColumnInfo(name = "author")
    val author: String = "",
    @ColumnInfo(name = "contributor")
    val contributor: String = "",
    @ColumnInfo(name = "book_image")
    val bookImage: String = "",
    @ColumnInfo(name = "amazon_product_url")
    val amazonProductUrl: String = "",
    @ColumnInfo(name = "age_group")
    val ageGroup: String = "",
    @ColumnInfo(name = "book_image_width")
    val bookImageWidth: Int = 0,
    @ColumnInfo(name = "book_image_height")
    val bookImageHeight: Int = 0,
    @ColumnInfo(name = "type")
    val type: String = "",
    @ColumnInfo(name = "fav")
    var fav: Boolean = false,
)

