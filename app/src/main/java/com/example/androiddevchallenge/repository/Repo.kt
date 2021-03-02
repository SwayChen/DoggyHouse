package com.example.androiddevchallenge.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repo(private val dataHelper: DataHelper) {
    suspend fun getDoggies() = withContext(Dispatchers.IO) {
        dataHelper.getDoggies()
    }
}