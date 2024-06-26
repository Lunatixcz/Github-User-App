package com.example.submissionawalfundamental.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.submissionawalfundamental.database.Favorite
import com.example.submissionawalfundamental.database.FavoriteDao
import com.example.submissionawalfundamental.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository (application: Application) {
    private val mFavoriteDao : FavoriteDao
    private val executorService : ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFavorites() : LiveData<List<Favorite>> = mFavoriteDao.getAllFavorite()
    fun insert (favorite: Favorite) {
        executorService.execute {mFavoriteDao.insert(favorite)}
    }
    fun delete (favorite: Favorite) {
        executorService.execute {mFavoriteDao.delete(favorite)}
    }
    fun checkIfFavorite (username: String) : LiveData<Boolean> {
        return mFavoriteDao.checkIfFavorite(username)
    }
}