package com.example.androiddevchallenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.model.DoggyModel
import com.example.androiddevchallenge.repository.Repo
import com.example.androiddevchallenge.repository.Status
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class MainViewModel(private val repo: Repo) : ViewModel() {
    val dogsLiveData: LiveData<Status<List<DoggyModel>>>
        get() = doggiesLiveData

    private val doggiesLiveData = MutableLiveData<Status<List<DoggyModel>>>()

    private val doggy = mutableListOf<DoggyModel>()

    private val handler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        doggiesLiveData.value = Status.failed(throwable.message ?: "Unknown exception")
    }

    fun parseData() = viewModelScope.launch(handler) {
        doggiesLiveData.value = Status.loading()
        doggy.addAll(repo.getDoggies())
        doggiesLiveData.value = Status.success(doggy)
    }

    fun setDogAdopted(position: Int) {
        doggy[position].adopted = true
        doggiesLiveData.value = doggiesLiveData.value
    }
}