package com.atsalaja.githubuserapp.di

import android.content.Context
import com.atsalaja.githubuserapp.data.database.FavUsersRoomDatabase
import com.atsalaja.githubuserapp.data.repository.FavUsersRepo
import com.atsalaja.githubuserapp.data.retrofit.ApiConfig
import com.atsalaja.githubuserapp.data.utils.AppExecutors

object Injection {
    fun provideRepo(context: Context): FavUsersRepo {
        val apiService = ApiConfig.getApiService()
        val database = FavUsersRoomDatabase.getDatabase(context)
        val dao = database.favUsersDao()
        val appExecutors = AppExecutors()
        return FavUsersRepo.getInstance(apiService, dao, appExecutors)
    }
}