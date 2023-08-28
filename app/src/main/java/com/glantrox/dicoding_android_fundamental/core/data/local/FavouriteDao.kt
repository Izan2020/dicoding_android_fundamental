package com.glantrox.dicoding_android_fundamental.core.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.glantrox.dicoding_android_fundamental.core.models.Favourite


@Dao
interface FavouriteDao {
    @Query("DELETE FROM users")
    suspend fun deleteAllFavourites()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(user: Favourite)

    @Delete
    suspend fun deleteFavourite(user: Favourite)

    @Query("SELECT * FROM users ORDER BY login ASC")
    fun getFavourites(): List<Favourite>
}