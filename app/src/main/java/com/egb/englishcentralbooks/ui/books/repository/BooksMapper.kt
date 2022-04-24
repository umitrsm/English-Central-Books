package com.egb.englishcentralbooks.ui.books.repository

import com.egb.englishcentralbooks.db.entity.BookDb
import com.egb.englishcentralbooks.db.entity.BookListDb
import com.egb.englishcentralbooks.network.datasource.Mapper
import com.egb.englishcentralbooks.network.models.Book
import com.egb.englishcentralbooks.network.models.BookList

interface BooksMapper : Mapper<Any, Any> {

    override fun Any.toStorage(type: String): Any {
        if (this is BookList)
            return BookListDb(
                list_name = list_name,
                display_name = display_name,
                list_name_encoded = list_name_encoded,
                oldest_published_date = oldest_published_date,
                newest_published_date = newest_published_date,
                updated = updated
            ) else if (this is Book) {
            return BookDb(
                description,
                price,
                title,
                author,
                contributor,
                bookImage,
                amazonProductUrl,
                ageGroup,
                bookImageWidth,
                bookImageHeight,
                type

            )
        }
        return Any()
    }

    override fun Any.toRemote(): Any {
        if (this is BookListDb) {
            return BookList(
                list_name = list_name,
                display_name = display_name,
                list_name_encoded = list_name_encoded,
                oldest_published_date = oldest_published_date,
                newest_published_date = newest_published_date,
                updated = updated
            )
        } else if (this is BookDb) {
            return Book(
                description,
                price,
                title,
                author,
                bookImageWidth,
                bookImageHeight,
                contributor,
                bookImage,
                amazonProductUrl,
                ageGroup,
                type
            )
        }
        return Any()
    }
}