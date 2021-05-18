package com.defconapplications.marvel.repository.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap


private const val APIKEY = "080a502746c8a60aeab043387a56eef0"
private const val HASH = "6edc18ab1a954d230c1f03c590d469d2"

private val defaultQueries : HashMap<String,String> by lazy {
    val map = HashMap<String,String>()
    map.put("ts", "1")
    map.put("apikey", APIKEY)
    map.put("hash", HASH)
    map.put("limit","25")
    map.put("offset", "0")
    map.put("orderBy", "-onsaleDate")
    map
}

interface ApiService {
    @GET("comics")
    fun getAllComics(@QueryMap queries : Map<String,String> = defaultQueries): Deferred<Response>

    @GET("comics")
    fun getFilteredComics(@Query("title") str: String, @QueryMap queries : Map<String,String> = defaultQueries): Deferred<Response>
}

val apiService : ApiService by lazy {
    val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl("https://gateway.marvel.com/v1/public/")
            .build()
    retrofit.create(ApiService::class.java)
}

