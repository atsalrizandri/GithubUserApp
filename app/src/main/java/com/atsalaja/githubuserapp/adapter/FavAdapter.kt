package com.atsalaja.githubuserapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atsalaja.githubuserapp.data.response.ItemsItem
import com.atsalaja.githubuserapp.databinding.ItemUsersBinding
import com.atsalaja.githubuserapp.ui.DetailUserActivity
import com.bumptech.glide.Glide

class FavAdapter: RecyclerView.Adapter<FavAdapter.FavViewHolder>() {
    private var listUsers = ArrayList<ItemsItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavViewHolder(binding)
    }

    inner class FavViewHolder(private val binding: ItemUsersBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem) {
            binding.tvItemUsername.text = user.login
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .into(binding.ivAvatar)
            itemView.setOnClickListener{
                val intentDetail = Intent(itemView.context, DetailUserActivity::class.java)
                intentDetail.putExtra(DetailUserActivity.username, user.login)
                itemView.context.startActivity(intentDetail)
            }
        }

    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val user = listUsers[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return listUsers.size
    }

    fun setListUsers(listUsers: ArrayList<ItemsItem>) {
        this.listUsers.clear()
        this.listUsers.addAll(listUsers)
        notifyDataSetChanged()
    }
}