package com.egb.englishcentralbooks.network.datasource

sealed class Resource<out T> {
    data class Success<T>(val data: T?) : Resource<T>()
    data class Error<T>(val message: String?, val data: T?) : Resource<T>()
    object Loading : Resource<Nothing>()
}

