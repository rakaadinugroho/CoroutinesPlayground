package com.rakaadinugroho.coroutinesplayground.network

import com.rakaadinugroho.coroutinesplayground.data.DocumentResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PlosService {
    @GET("search")
    suspend fun searchDocument(@Query("q") keyword: String): DocumentResponse
}