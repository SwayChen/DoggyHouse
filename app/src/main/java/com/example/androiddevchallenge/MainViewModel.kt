/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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