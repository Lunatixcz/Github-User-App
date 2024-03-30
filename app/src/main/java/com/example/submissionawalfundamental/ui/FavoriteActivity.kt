package com.example.submissionawalfundamental.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionawalfundamental.databinding.ActivityFavoriteBinding
import com.example.submissionawalfundamental.helper.FavoriteViewModelFactory
import com.example.submissionawalfundamental.ui.adapter.FavoriteAdapter
import com.example.submissionawalfundamental.viewModel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite"

        binding.rvFavorite.layoutManager = LinearLayoutManager (this)
        favoriteAdapter = FavoriteAdapter()
        binding.rvFavorite.adapter = favoriteAdapter

        val factory = FavoriteViewModelFactory.getInstance(application)
        favoriteViewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

        favoriteViewModel.getAllFavorites().observe(this) { favorite ->
            favoriteAdapter.submitList(favorite)
        }
    }
}