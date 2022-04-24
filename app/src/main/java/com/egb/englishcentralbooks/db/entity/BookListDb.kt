package com.egb.englishcentralbooks.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "book_list")
data class BookListDb(
    @ColumnInfo(name = "list_name")
    val list_name: String = "",
    @ColumnInfo(name = "display_name")
    val display_name: String = "",
    @ColumnInfo(name = "list_name_encoded")
    @PrimaryKey
    val list_name_encoded: String = "",
    @ColumnInfo(name = "oldest_published_date")
    val oldest_published_date: String = "",
    @ColumnInfo(name = "newest_published_date")
    val newest_published_date: String = "",
    @ColumnInfo(name = "updated")
    val updated: String = "",
    )