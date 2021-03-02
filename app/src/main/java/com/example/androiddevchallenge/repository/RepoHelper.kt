package com.example.androiddevchallenge.repository

object RepoHelper {
    fun provideRepository() = Repo(provideDataHelper())

    private fun provideDataHelper() = DataHelper()
}