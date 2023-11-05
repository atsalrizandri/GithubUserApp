package com.atsalaja.githubuserapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.atsalaja.githubuserapp.data.response.GithubResponse
import com.atsalaja.githubuserapp.data.response.ItemsItem
import com.atsalaja.githubuserapp.data.retrofit.ApiConfig
import com.atsalaja.githubuserapp.ui.settings.SettingPreferences
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: SettingPreferences): ViewModel(){
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listUsers = MutableLiveData<List<ItemsItem>?>()
    val listUsers: MutableLiveData<List<ItemsItem>?> = _listUsers

    init {
        findUsers()
    }

    private fun findUsers() {
        _isLoading.value = true

        val client = ApiConfig.getApiService().searchUsers(Q)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _listUsers.value = response.body()?.items
                } else {
                    Log.e(TAG, "onfailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun searchUsers(query: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().searchUsers(query)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _listUsers.value = response.body()?.items
                } else {
                    Log.e(TAG, "onfailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getThemeSetting(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
        private const val Q = "Atsal"
    }
}