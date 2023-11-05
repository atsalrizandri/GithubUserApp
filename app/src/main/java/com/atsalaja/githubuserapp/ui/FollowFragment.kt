package com.atsalaja.githubuserapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.atsalaja.githubuserapp.R
import com.atsalaja.githubuserapp.adapter.ListUsersAdapter
import com.atsalaja.githubuserapp.data.response.ItemsItem
import com.atsalaja.githubuserapp.databinding.FragmentFollowBinding
import com.atsalaja.githubuserapp.viewmodel.DetailViewModel

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follow, container, false)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFollowBinding.bind(view)

        val detailUserViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvUsers.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        val username = arguments?.getString(ARG_USERNAME)
        val position = arguments?.getInt(ARG_POSITION)

        if (position == 1) {
            detailUserViewModel.getFollowers(username.toString())
            detailUserViewModel.listFollowers.observe(viewLifecycleOwner) { listFollowers ->
                setListFollow(listFollowers)
            }

            detailUserViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        } else {
            detailUserViewModel.getFollowing(username.toString())
            detailUserViewModel.listFollowing.observe(viewLifecycleOwner) { listFollowing ->
                setListFollow(listFollowing)
            }

            detailUserViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        }
    }

    private fun setListFollow(listFollowUser: List<ItemsItem>) {
        val adapter = ListUsersAdapter()
        adapter.submitList(listFollowUser)
        binding.rvUsers.adapter = adapter
    }

    companion object {
        const val ARG_USERNAME = "username"
        const val ARG_POSITION = "position"
    }
}