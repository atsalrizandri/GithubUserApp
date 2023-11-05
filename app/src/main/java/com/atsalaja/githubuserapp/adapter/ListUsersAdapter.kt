package com.atsalaja.githubuserapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.atsalaja.githubuserapp.data.response.ItemsItem
import com.atsalaja.githubuserapp.databinding.ItemUsersBinding
import com.atsalaja.githubuserapp.ui.DetailUserActivity
import com.bumptech.glide.Glide

class ListUsersAdapter : ListAdapter<ItemsItem, ListUsersAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem) {
            binding.tvItemUsername.text = user.login
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .into(binding.ivAvatar)
            itemView.setOnClickListener {
                val intentUserDetail = Intent(itemView.context, DetailUserActivity::class.java)
                intentUserDetail.putExtra(DetailUserActivity.username, user.login)
                itemView.context.startActivity(intentUserDetail)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }
}