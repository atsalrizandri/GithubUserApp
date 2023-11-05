package com.atsalaja.githubuserapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.atsalaja.githubuserapp.data.database.FavUsers
import com.atsalaja.githubuserapp.data.repository.FavUsersRepo

class FavUsersViewModel(private val repo: FavUsersRepo): ViewModel() {
    fun setFavUsers(favUsers: FavUsers) {
        repo.setFavUsers(favUsers)
    }

    fun getFavUsers() : LiveData<List<FavUsers>> = repo.getFavUsers()

    fun getFavUsersByUsername(username: String): LiveData<FavUsers> = repo.getFavUsersByUsername(username)

    fun delFavUsers(username: String){
        repo.delFavUsers(username)
    }
}