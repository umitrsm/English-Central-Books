package com.egb.englishcentralbooks.ui.books

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egb.englishcentralbooks.db.entity.BookDb
import com.egb.englishcentralbooks.db.entity.BookListDb
import com.egb.englishcentralbooks.network.datasource.Resource
import com.egb.englishcentralbooks.ui.books.repository.BooksRepositoryImpl
import com.egb.englishcentralbooks.extensions.logError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class BooksViewModel @Inject constructor(
    private val application: Application,
    private val repository: BooksRepositoryImpl
) : ViewModel() {


    private val _bookList = MutableLiveData<Resource<List<BookListDb>>>()
    val bookList: LiveData<Resource<List<BookListDb>>> = _bookList

    private val _books = MutableLiveData<Resource<List<BookDb>>>()
    val books: LiveData<Resource<List<BookDb>>> = _books

    private val _selectedBookType = MutableLiveData<BookListDb>()
    val selectedBookType: LiveData<BookListDb> = _selectedBookType

    private val _favs = MutableLiveData<List<BookDb>>()
    val favs: LiveData<List<BookDb>> = _favs

    fun fetchBooksList() {
        viewModelScope.launch {
            if (handleInternetConnection()) {
                repository.getBookTypes()
                repository.fetchBookList().collect {
                    _bookList.postValue(Resource.Success(it))
                }
            } else {
                repository.fetchBookList().collect {
                    _bookList.postValue(Resource.Error("There is No Internet Connection", it))
                }
                logError("There is No Internet Connection")
            }

        }
    }

    fun fetchFavList() {
        viewModelScope.launch {
            repository.fetchFavListFromDb().collect {
                _favs.postValue(it)
            }
        }
    }

    fun deleteFav(fav: BookDb) {
        viewModelScope.launch {
            if (repository.deleteFavToDb(fav))  {
                fetchFavList()
            }
        }
    }

    fun addFav(fav: BookDb) {
        viewModelScope.launch {
            if (repository.addFavToDb(fav)) {
                fetchFavList()
            }
        }
    }

    fun selectBookType(bookListDb: BookListDb) {
        _selectedBookType.postValue(bookListDb)
        fetchBooksByType(bookListDb.list_name_encoded)
    }


    private fun fetchBooksByType(type: String) {
        viewModelScope.launch {
            if (handleInternetConnection()) {
                repository.getBookListbyType(type)
                repository.fetchBooksByType(type).collect {
                    _books.postValue(Resource.Success(it))
                }
            } else {
                repository.fetchBooksByType(type).collect {
                    _books.postValue(Resource.Error("There is No Internet Connection", it))
                }
                logError("There is No Internet Connection")
            }

        }
    }

    private fun handleInternetConnection(): Boolean {
        val connectivityManager = application.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}