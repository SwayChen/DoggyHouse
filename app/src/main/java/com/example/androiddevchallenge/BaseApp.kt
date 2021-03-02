package com.example.androiddevchallenge

import android.app.Application
import android.content.Context

const val SELECTED_DOGGY = "selected_doggy"
const val SELECTED_POSITION = "selected_position"
const val ADOPTED = "adopted"

class BaseApp : Application() {
    companion object {
        @SuppressWarnings("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}