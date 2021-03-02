package com.example.androiddevchallenge.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class DoggyModel(
    val name: String,
    val avatar: String,
    val temperament: String,
    val desc: String,
    var adopted: Boolean
) : Parcelable