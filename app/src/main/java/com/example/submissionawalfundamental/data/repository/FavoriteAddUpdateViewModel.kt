package com.example.submissionawalfundamental.data.repository

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.submissionawalfundamental.database.Favorite

class FavoriteAddUpdateViewModel (application: Application) : AndroidViewModel(application) {
    private val mFavoriteRepository : FavoriteRepository = FavoriteRepository(application)

    fun insert (favorite: Favorite) {
        mFavoriteRepository.insert(favorite)
    }
    fun delete (favorite: Favorite) {
        mFavoriteRepository.delete(favorite)
    }
}