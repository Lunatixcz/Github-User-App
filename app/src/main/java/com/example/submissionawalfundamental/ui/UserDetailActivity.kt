package com.example.submissionawalfundamental.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.submissionawalfundamental.R
import com.example.submissionawalfundamental.data.repository.FavoriteAddUpdateViewModel
import com.example.submissionawalfundamental.data.response.DetailUserResponse
import com.example.submissionawalfundamental.database.Favorite
import com.example.submissionawalfundamental.databinding.ActivityUserDetailActivityBinding
import com.example.submissionawalfundamental.helper.FavoriteViewModelFactory
import com.example.submissionawalfundamental.ui.adapter.SectionsPagerAdapter
import com.example.submissionawalfundamental.viewModel.FavoriteViewModel
import com.example.submissionawalfundamental.viewModel.UserDetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityUserDetailActivityBinding
    private lateinit var userDetailViewModel : UserDetailViewModel
    private lateinit var favoriteAddUpdateViewModel: FavoriteAddUpdateViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "User Detail"

        val username = intent.getStringExtra("USERNAME")

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        if (username != null) {
            sectionsPagerAdapter.username = username
        }

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) {tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.follower)
                1 -> getString(R.string.following)
                else -> ""
            }
        }.attach()

        val favoriteViewModelFactory = FavoriteViewModelFactory(application)

        userDetailViewModel = ViewModelProvider(this, favoriteViewModelFactory)[UserDetailViewModel::class.java]
        favoriteAddUpdateViewModel = ViewModelProvider(this, favoriteViewModelFactory)[FavoriteAddUpdateViewModel::class.java]
        favoriteViewModel = ViewModelProvider(this, favoriteViewModelFactory)[FavoriteViewModel::class.java]

        if (username != null) {
            if (userDetailViewModel.username.value == null) {
                userDetailViewModel.findDetail(username)
            }
            userDetailViewModel.detail.observe(this){ detail ->
                if (detail != null) {
                    bindUser(detail)
                }
            }
            userDetailViewModel.isLoading.observe(this) {
                showLoading(it)
            }

            userDetailViewModel.errorMessage.observe(this) { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }

            checkIfFavorite(username)

        } else {
            val message = "transfer error"
            Toast.makeText(this ,message, Toast.LENGTH_SHORT).show()
        }

        binding.fabFavorite.setOnClickListener{
            if (username != null) {
                checkIfFavorite(username)
            }
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun checkIfFavorite(username : String) {
        userDetailViewModel.checkIfFavorite(username).observe(this) { isFavorite ->
            if (isFavorite) {
                binding.fabFavorite.setImageResource(R.drawable.ic_favorite_filled)
                binding.fabFavorite.setOnClickListener{
                    deleteFromDatabase(username)
                }
            } else {
                binding.fabFavorite.setImageResource(R.drawable.ic_favorite_border)
                binding.fabFavorite.setOnClickListener {
                    saveToDatabase()
                }
            }
        }
    }
    private fun deleteFromDatabase(username: String) {
        val avatarUrl = intent.getStringExtra("AVATARURL") ?: return

        val favorite = Favorite (username = username, avatarUrl = avatarUrl)
        favoriteAddUpdateViewModel.delete(favorite)

        Toast.makeText(this, "User berhasil dihapus", Toast.LENGTH_SHORT).show()
    }

    private fun saveToDatabase() {
        val username = intent.getStringExtra("USERNAME") ?: return
        val avatarUrl = intent.getStringExtra("AVATARURL")
        val favorite = Favorite(username = username, avatarUrl = avatarUrl)
        favoriteAddUpdateViewModel.insert(favorite)

        Toast.makeText(this, "User berhasil ditambahan ke favorite", Toast.LENGTH_SHORT).show()
    }
}