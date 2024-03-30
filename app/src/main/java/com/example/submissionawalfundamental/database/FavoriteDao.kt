package com.example.submissionawalfundamental.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {

    @Insert(onConflict  = OnConflictStrategy.IGNORE)
    fun insert (favorite: Favorite)

    @Delete
    fun delete (favorite: Favorite)

    @Query("SELECT EXISTS(SELECT 1 FROM Favorite WHERE username = :username LIMIT 1)")
    fun checkIfFavorite (username: String): LiveData<Boolean>
    @Query("SELECT * FROM Favorite")
    fun getAllFavorite(): LiveData<List<Favorite>>
}