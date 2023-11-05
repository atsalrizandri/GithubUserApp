package com.atsalaja.githubuserapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.atsalaja.githubuserapp.R
import com.atsalaja.githubuserapp.adapter.ListUsersAdapter
import com.atsalaja.githubuserapp.data.response.ItemsItem
import com.atsalaja.githubuserapp.databinding.ActivityMainBinding
import com.atsalaja.githubuserapp.ui.settings.SettingActivity
import com.atsalaja.githubuserapp.ui.settings.SettingPreferences
import com.atsalaja.githubuserapp.ui.settings.dataStore
import com.atsalaja.githubuserapp.viewmodel.MainViewModel
import com.atsalaja.githubuserapp.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val mainViewModel by viewModels<MainViewModel>{
            ViewModelFactory.getInstance(this.application, pref)
        }

        mainViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val adapter = ListUsersAdapter()
        binding.rvUsers.adapter = adapter

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        mainViewModel.listUsers.observe(this) { listUsers ->
            setListUsers(listUsers)
        }

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    mainViewModel.searchUsers(searchBar.text.toString())
                    false
                }
        }

        binding.searchBar.inflateMenu(R.menu.bar_menu)
        binding.searchBar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.mBar_Fav -> {
                    val intentFavUsers = Intent(this@MainActivity, FavUsersActivity::class.java)
                    startActivity(intentFavUsers)
                    true
                }
                R.id.mBar_Setting -> {
                    val intentSetting = Intent(this@MainActivity, SettingActivity::class.java)
                    startActivity(intentSetting)
                    true
                } else -> false
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setListUsers(listUsers: List<ItemsItem>?) {
        val adapter = ListUsersAdapter()
        adapter.submitList(listUsers)
        binding.rvUsers.adapter = adapter
    }
}