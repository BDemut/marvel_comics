package com.defconapplications.marvel.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.defconapplications.marvel.repository.network.apiService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
        @ApplicationContext appContext: Context
) {
    val database : ComicDatabase = Room.databaseBuilder(appContext.applicationContext,
            ComicDatabase::class.java,
            "comics").build()
    private val sharedPref = appContext.getSharedPreferences("cache_data",Context.MODE_PRIVATE)

    val comics: LiveData<List<Comic>> = database.comicDao.getComics()

    val cachedWord = MutableLiveData<String?>()

    init {
        cachedWord.value = sharedPref?.getString("word", null)
    }
    
    fun getComic(id : Int) : LiveData<Comic> {
        return database.comicDao.getComic(id)
    }

    suspend fun fetchComics(str: String? = null): String? {
        val errorMessage = withContext(Dispatchers.IO) {
            try {
                if (str == null) {
                    val c = apiService.getAllComics().await().data.results
                    database.comicDao.replace(*c.asRoomComics())
                    sharedPref?.edit()?.remove("word")?.apply()
                } else {
                    val c = apiService.getFilteredComics(str=str).await().data.results
                    database.comicDao.replace(*c.asRoomComics())
                    sharedPref?.edit()?.putString("word", str)?.apply()
                }
                null
            } catch (e : Exception) {
                "Network call faliure: ${e.message}"
            }
        }
        if (errorMessage == null)
            cachedWord.value = str
        return errorMessage
    }
}
