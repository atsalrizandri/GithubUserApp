package com.atsalaja.githubuserapp.data.repository

import androidx.lifecycle.LiveData
import com.atsalaja.githubuserapp.data.database.FavUsers
import com.atsalaja.githubuserapp.data.database.FavUsersDao
import com.atsalaja.githubuserapp.data.retrofit.ApiService
import com.atsalaja.githubuserapp.data.utils.AppExecutors

class FavUsersRepo(
    private val apiService: ApiService,
    private var favUsersDao: FavUsersDao,
    private val appExecutors: AppExecutors
) {
    fun setFavUsers(favUsers: FavUsers){
        appExecutors.diskIO.execute {
            favUsersDao.insert(favUsers)
        }
    }

    fun getFavUsers(): LiveData<List<FavUsers>> {
        return favUsersDao.getUsersFav()
    }

    fun getFavUsersByUsername(username: String): LiveData<FavUsers> {
        return favUsersDao.getUsersFavByUsername(username)
    }

    fun delFavUsers(username: String){
        appExecutors.diskIO.execute {
            favUsersDao.deleteUsersFav(username)
        }
    }

    companion object{
        @Volatile
        private var instance: FavUsersRepo? = null

        fun getInstance(apiService: ApiService, dao: FavUsersDao, appExecutors: AppExecutors): FavUsersRepo =
            instance ?: synchronized(this) {
                instance ?: FavUsersRepo(apiService, dao, appExecutors)
            }.also { instance = it }
    }
}