package com.atsalaja.githubuserapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.atsalaja.githubuserapp.data.repository.FavUsersRepo
import com.atsalaja.githubuserapp.di.Injection
import com.atsalaja.githubuserapp.ui.settings.SettingPreferences

class ViewModelFactory(private val repo: FavUsersRepo, private val pref: SettingPreferences): ViewModelProvider.NewInstanceFactory() {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavUsersViewModel::class.java)) {
            return FavUsersViewModel(repo) as T
        }

        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class: " + modelClass.name)
    }

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(repo: Application, pref: SettingPreferences): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepo(repo), pref)
            }.also { instance = it }
    }
}