package com.example.submissionawalfundamental.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submissionawalfundamental.database.Favorite
import com.example.submissionawalfundamental.data.repository.FavoriteRepository

class FavoriteViewModel (private val repository: FavoriteRepository) : ViewModel() {
    fun getAllFavorites() : LiveData<List<Favorite>> {
        return repository.getAllFavorites()
    }
}