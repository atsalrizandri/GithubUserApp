package com.atsalaja.githubuserapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.atsalaja.githubuserapp.adapter.FavAdapter
import com.atsalaja.githubuserapp.data.response.ItemsItem
import com.atsalaja.githubuserapp.databinding.ActivityFavUsersBinding
import com.atsalaja.githubuserapp.ui.settings.SettingPreferences
import com.atsalaja.githubuserapp.ui.settings.dataStore
import com.atsalaja.githubuserapp.viewmodel.FavUsersViewModel
import com.atsalaja.githubuserapp.viewmodel.ViewModelFactory

class FavUsersActivity: AppCompatActivity() {
    private lateinit var binding: ActivityFavUsersBinding
    private lateinit var adapter: FavAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = FavAdapter()

        val pref = SettingPreferences.getInstance(application.dataStore)
        val favViewModel by viewModels<FavUsersViewModel> {
            ViewModelFactory.getInstance(this.application, pref)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        favViewModel.getFavUsers().observe(this) { listUsers ->
            val user = ArrayList<ItemsItem>()
            listUsers.map {
                user.add(ItemsItem(login = it.username, avatarUrl = it.avatarUrl))
            }
            adapter.setListUsers(user)
        }
        binding.rvUsers.adapter = adapter
    }
}