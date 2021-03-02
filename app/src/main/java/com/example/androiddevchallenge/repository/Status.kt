package com.example.androiddevchallenge.repository

data class Status<T>(val status: Int, val data: T?, val message: String?) {

    companion object {
        const val SUCCESS = 0
        const val ERROR = 1
        const val LOADING = 2

        fun <T> success(data: T) = Status(SUCCESS, data, null)
        fun <T> failed(msg: String) = Status<T>(ERROR, null, msg)
        fun <T> loading() = Status<T>(LOADING, null, null)
    }
}