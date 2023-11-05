package com.atsalaja.githubuserapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.atsalaja.githubuserapp.R
import com.atsalaja.githubuserapp.adapter.SectionsPagerAdapter
import com.atsalaja.githubuserapp.data.database.FavUsers
import com.atsalaja.githubuserapp.data.response.DetailUserResponse
import com.atsalaja.githubuserapp.databinding.ActivityDetailUserBinding
import com.atsalaja.githubuserapp.ui.settings.SettingPreferences
import com.atsalaja.githubuserapp.ui.settings.dataStore
import com.atsalaja.githubuserapp.viewmodel.DetailViewModel
import com.atsalaja.githubuserapp.viewmodel.FavUsersViewModel
import com.atsalaja.githubuserapp.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private var favUsers: FavUsers? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailUserViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]

        val pref = SettingPreferences.getInstance(application.dataStore)
        val detailViewModel by viewModels<FavUsersViewModel> {
            ViewModelFactory.getInstance(this.application, pref)
        }

        val username = intent.getStringExtra(username)
        detailUserViewModel.getDetailUser(username.toString())

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username.toString()

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        detailUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        favUsers = FavUsers()

        detailUserViewModel.userDetail.observe(this) { userDetail ->
            setUserDetailData(userDetail)

            val tabTittle = resources.getStringArray(R.array.tab_titles_fragment)
            tabTittle[0] = userDetail.followers?.let { getString(R.string.followers, it) } ?: getString(R.string.followers, "0")
            tabTittle[1] = userDetail.following?.let { getString(R.string.following, it) } ?: getString(R.string.following, "0")

            TabLayoutMediator(binding.tabLayoutMenuFragment, viewPager) { tab, position ->
                tab.text = tabTittle[position]
            }.attach()
            supportActionBar?.elevation = 0f

            favUsers.let {
                favUsers?.username = userDetail.login.toString()
                favUsers?.avatarUrl = userDetail.avatarUrl.toString()
            }
        }

        detailViewModel.getFavUsersByUsername(username.toString()).observe(this){
            if (it != null) {
                binding.ivFav.setImageResource(R.drawable.ic_fav_isfill)
                binding.ivFav.setOnClickListener{
                    detailViewModel.delFavUsers(username.toString())
                }
            } else {
                binding.ivFav.setImageResource(R.drawable.ic_fav_unfill)
                binding.ivFav.setOnClickListener{
                    detailViewModel.setFavUsers(favUsers!!)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setUserDetailData(userDetail: DetailUserResponse) {
        with(binding) {
            tvItemUsername.text = userDetail.login
            tvName.text = userDetail.name
            Glide.with(this@DetailUserActivity)
                .load(userDetail.avatarUrl)
                .into(ivAvatar)
        }
    }

    companion object{
        const val username = "username"
    }
}