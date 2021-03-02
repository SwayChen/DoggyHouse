package com.example.androiddevchallenge.repository

import com.example.androiddevchallenge.BaseApp
import com.example.androiddevchallenge.model.DoggyModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class DataHelper {
    suspend fun getDoggies(): List<DoggyModel> = coroutineScope {
        var doggyList: List<DoggyModel> = ArrayList()
        val job = launch(Dispatchers.IO) {
            try {
                val jsonString = BufferedReader(InputStreamReader(BaseApp.context.assets.open("doggies.json"))).readText()
                val typeOf = object : TypeToken<List<DoggyModel>>() {}.type
                doggyList = Gson().fromJson(jsonString, typeOf)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        job.join()
        doggyList
    }
}