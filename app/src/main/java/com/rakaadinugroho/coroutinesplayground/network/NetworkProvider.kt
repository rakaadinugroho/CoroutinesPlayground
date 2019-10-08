package com.rakaadinugroho.coroutinesplayground.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkProvider {
    fun providePlosClient(): PlosService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.plos.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(PlosService::class.java)
    }
}