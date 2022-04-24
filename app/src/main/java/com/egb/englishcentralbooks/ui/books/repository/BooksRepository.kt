package com.egb.englishcentralbooks.ui.books.repository

import com.egb.englishcentralbooks.db.entity.BookDb
import com.egb.englishcentralbooks.db.entity.BookListDb
import kotlinx.coroutines.flow.Flow

interface BooksRepository {
    suspend fun fetchBookList(): Flow<List<BookListDb>>

    suspend fun fetchBooksByType(type: String): Flow<List<BookDb>>

    suspend fun fetchFavListFromDb(): Flow<List<BookDb>>

    suspend fun addFavToDb(fav: BookDb): Boolean

    suspend fun deleteFavToDb(fav: BookDb): Boolean

    suspend fun getBookTypes()

    suspend fun getBookListbyType(type: String)
}