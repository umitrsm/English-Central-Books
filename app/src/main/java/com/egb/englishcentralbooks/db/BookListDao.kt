package com.egb.englishcentralbooks.db

import androidx.room.*
import com.egb.englishcentralbooks.db.entity.BookDb
import com.egb.englishcentralbooks.db.entity.BookListDb

@Dao
interface BookListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookListResponse: List<BookListDb>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBooks(bookListResponse: List<BookDb>)

    @Query("UPDATE books SET fav = :isFav WHERE title = :fav")
    suspend fun updateFav(fav: String, isFav: Boolean? = false)

    @Query("SELECT * FROM books WHERE fav = :fav")
    fun getFavList(fav: Boolean = true): List<BookDb>

    @Query("SELECT * FROM book_list")
    fun getBookList(): List<BookListDb>

    @Query("SELECT * FROM books WHERE type = :type")
    fun getBooks(type: String): List<BookDb>

}