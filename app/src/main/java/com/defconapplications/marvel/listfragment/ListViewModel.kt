package com.defconapplications.marvel.listfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.defconapplications.marvel.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    val errorMessage = MutableLiveData<String?>(null)
    val comicList = repository.comics
    val comicListUpdateEvent = MutableLiveData(false)
    private var job = Job()

    enum class State {
        LISTING,
        LOADING,
        HELP_NO_COMICS,
        HELP_NO_SEARCHWORD
    }
    val state = MutableLiveData(State.LOADING)

    val searchWord = MutableLiveData<String?>(repository.cachedWord.value)
    val searchActionActive = MutableLiveData(repository.cachedWord.value != null)


    fun fetchComicData() {
        job.cancel()
        job = Job()
        CoroutineScope(Dispatchers.Main + job).launch {
            if (searchWord.value != "") {    //network call fails with an empty title
                errorMessage.value = repository.fetchComics(searchWord.value)
            }
            else
                errorMessage.value = repository.fetchComics(null)
            /**
             * makes sure the event fires even if the list doesn't change
             * (usually when it goes from empty to empty while inputting the search term)
             */
            if (comicList.value!!.isEmpty())
                comicListUpdateEvent.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}