package com.egb.englishcentralbooks.ui.books.repository

import com.egb.englishcentralbooks.db.BookListDao
import com.egb.englishcentralbooks.db.entity.BookDb
import com.egb.englishcentralbooks.db.entity.BookListDb
import com.egb.englishcentralbooks.extensions.logInfo
import com.egb.englishcentralbooks.network.BooksService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BooksRepositoryImpl @Inject constructor(private val booksService: BooksService, private val booksDao: BookListDao) :
    BooksRepository,
    BooksMapper {

    override suspend fun fetchBookList(): Flow<List<BookListDb>> = flow {
        val bookListStorage = booksDao.getBookList()
        logInfo("response size -> ${bookListStorage.size}")
        emit(bookListStorage)
    }

    override suspend fun fetchBooksByType(type: String): Flow<List<BookDb>> = flow {
        val booksStorage = booksDao.getBooks(type)
        logInfo("response size -> ${booksStorage.size}")
        emit(booksStorage)
    }

    override suspend fun fetchFavListFromDb(): Flow<List<BookDb>> = flow {
        val favStorageStorage = booksDao.getFavList()
        logInfo("response size -> ${favStorageStorage.size}")
        emit(favStorageStorage)
    }

    override suspend fun addFavToDb(fav: BookDb): Boolean {
        booksDao.updateFav(fav = fav.title, true)
        return true
    }

    override suspend fun deleteFavToDb(fav: BookDb): Boolean {
        booksDao.updateFav(fav.title)
        return true
    }

    override suspend fun getBookTypes() {
        val bookListRemote = booksService.getList()
        bookListRemote.results.toStorage().let {
            if (!it.isNullOrEmpty()) {
                booksDao.insert(it as List<BookListDb>)
            }
        }
    }

    override suspend fun getBookListbyType(type: String) {
        val books = booksService.getBooks(type)
        books.results?.books?.toStorage(type).let {
            if (it?.size != 0) {
                booksDao.insertBooks(it as List<BookDb>)
            }
        }
    }
}

