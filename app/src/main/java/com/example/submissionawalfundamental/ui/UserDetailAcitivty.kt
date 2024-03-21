package com.example.submissionawalfundamental.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.submissionawalfundamental.data.response.DetailUserResponse
import com.example.submissionawalfundamental.data.response.ItemsItem
import com.example.submissionawalfundamental.databinding.ActivityUserDetailAcitivtyBinding
import com.example.submissionawalfundamental.viewModel.UserDetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailAcitivty : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailAcitivtyBinding
    private val userDetailViewModel by viewModels<UserDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailAcitivtyBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        val item = intent.getSerializableExtra("ITEM_DETAIL") as? ItemsItem

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        if (item != null) {
            sectionsPagerAdapter.username = item.login.orEmpty()
        }

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) {tab, position ->
            tab.text = when (position) {
                0 -> "Followers"
                1 -> "Following"
                else -> ""
            }
        }.attach()

        if (item != null) {
            if (userDetailViewModel.username.value == null) {
                userDetailViewModel.findDetail(item.login.orEmpty())
                userDetailViewModel.setUsername(item.login)
            }

            userDetailViewModel.detail.observe(this){ detail ->
                if (detail != null) {
                    bindUser(detail)
                }

            }
        } else {

        }
    }
    private fun bindUser (detail : DetailUserResponse) {
        Glide.with(this)
            .load(detail.avatarUrl)
            .into(binding.ivAvatar)
        binding.tvName.text = detail.name
        binding.tvUsername.text = detail.login
        binding.tvFollower.text = "${detail.followers.toString()} Followers"
        binding.tvFollowing.text = "${detail.following.toString()} Following"
    }
}