package com.egb.englishcentralbooks.network

import com.egb.englishcentralbooks.network.models.BookListResponse
import com.egb.englishcentralbooks.network.models.BooksByTypeResponse
import com.egb.englishcentralbooks.utils.Constants.Companion.MY_API_KEY
import retrofit2.http.GET
import retrofit2.http.Path


interface BooksService {

    @GET("lists/names.json?api-key=${MY_API_KEY}")
    suspend fun getList(): BookListResponse

    @GET("lists/current/{type}.json?api-key=${MY_API_KEY}")
    suspend fun getBooks(@Path("type") type: String): BooksByTypeResponse
}