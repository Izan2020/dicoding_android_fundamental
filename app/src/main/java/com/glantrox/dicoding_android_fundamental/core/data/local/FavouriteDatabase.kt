package com.glantrox.dicoding_android_fundamental.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.glantrox.dicoding_android_fundamental.core.models.Favourite


@Database(entities = [Favourite::class], version = 2, exportSchema = false)
abstract class FavouriteDatabase : RoomDatabase() {
    abstract fun FavouriteDao(): FavouriteDao


}