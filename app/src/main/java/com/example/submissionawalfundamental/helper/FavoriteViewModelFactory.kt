package com.example.submissionawalfundamental.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submissionawalfundamental.data.repository.FavoriteAddUpdateViewModel
import com.example.submissionawalfundamental.data.repository.FavoriteRepository
import com.example.submissionawalfundamental.viewModel.FavoriteViewModel
import com.example.submissionawalfundamental.viewModel.UserDetailViewModel

class FavoriteViewModelFactory (private val mApplication: Application) : ViewModelProvider.Factory{

    private val favoriteRepository : FavoriteRepository by lazy {
        FavoriteRepository(mApplication)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserDetailViewModel::class.java)){
        return UserDetailViewModel(mApplication) as T
    } else if (modelClass.isAssignableFrom(FavoriteAddUpdateViewModel::class.java)) {
        return FavoriteAddUpdateViewModel(mApplication) as T
    } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
        return FavoriteViewModel(favoriteRepository) as T
    }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
    companion object {
        @Volatile
        private var INSTANCE: FavoriteViewModelFactory? = null

        fun getInstance(application: Application): FavoriteViewModelFactory {
            if (INSTANCE == null) {
                synchronized(FavoriteViewModelFactory::class.java) {
                    INSTANCE = FavoriteViewModelFactory(application)
                }
            }
            return INSTANCE as FavoriteViewModelFactory
        }
    }
}

