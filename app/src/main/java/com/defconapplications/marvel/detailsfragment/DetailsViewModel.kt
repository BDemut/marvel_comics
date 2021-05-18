package com.defconapplications.marvel.detailsfragment

import androidx.lifecycle.*
import com.defconapplications.marvel.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
        savedStateHandle : SavedStateHandle,
        val repository: Repository) : ViewModel() {

    val comic = repository.getComic(
            savedStateHandle["id"] ?: throw IllegalArgumentException("missing id"))
    var errorMessage = MutableLiveData<String?>(null)
}