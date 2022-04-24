package com.egb.englishcentralbooks.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.egb.englishcentralbooks.db.entity.BookDb
import com.egb.englishcentralbooks.db.entity.BookListDb


@Database(entities = [BookListDb::class, BookDb::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookListDao
}