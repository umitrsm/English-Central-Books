package com.egb.englishcentralbooks.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.egb.englishcentralbooks.db.entity.BookDb
import com.egb.englishcentralbooks.db.entity.BookListDb


@Database(
    entities = [BookListDb::class, BookDb::class],
    version = 1
)
abstract class BookListDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var instance: BookListDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                BookListDatabase::class.java,
                "book_list_db.db"
            ).build()

    }

    abstract fun getBookList(): BookListDao

}