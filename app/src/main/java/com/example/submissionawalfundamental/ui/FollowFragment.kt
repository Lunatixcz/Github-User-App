package com.example.submissionawalfundamental.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionawalfundamental.ui.adapter.SearchAdapter
import com.example.submissionawalfundamental.data.response.ItemsItem
import com.example.submissionawalfundamental.data.retrofit.ApiConfig
import com.example.submissionawalfundamental.databinding.FragmentFollowBinding
import com.example.submissionawalfundamental.viewModel.FollowViewModel

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private val followViewModel by viewModels<FollowViewModel>()

    private var position: Int = 0
    private var username: String =""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME) ?: ""
        }

        if (position == 1) {
            followViewModel.fetchData(ApiConfig.getApiService().getFollowers(username))
            followViewModel.dataFetched.observe(viewLifecycleOwner) {dataFetched ->
                showRecycleView(dataFetched)
            }
        } else {
            followViewModel.fetchData(ApiConfig.getApiService().getFollowing(username))
            followViewModel.dataFetched.observe(viewLifecycleOwner) {dataFetched ->
                showRecycleView(dataFetched)
            }
        }
        followViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }
        followViewModel.errorMessage.observe(viewLifecycleOwner){ message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun showRecycleView(items: List<ItemsItem>?) {
        binding.rvFollow.layoutManager = LinearLayoutManager(requireContext())

        val adapter = SearchAdapter()
        binding.rvFollow.adapter = adapter

        items?.let {
            adapter.submitList(it)
        }
    }
    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}