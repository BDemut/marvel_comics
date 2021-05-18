package com.defconapplications.marvel

import com.defconapplications.marvel.repository.network.ApiService
import com.defconapplications.marvel.repository.network.apiService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.nhaarman.mockitokotlin2.whenever
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiUnitTest {
    @Test
    fun getComicsSucceeds () = runBlocking {
        println(apiService.getAllComics().await())
        assert(true)
    }

    @Test
    fun getFilteredComicsSucceeds () = runBlocking {
        println(apiService.getFilteredComics("ant-man").await())
        assert(true)
    }
}