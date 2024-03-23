package com.dev.githubuser.data.local

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteUserDao {
    @Query("SELECT count(*) FROM favorite_user WHERE id = :id")
    fun checkUser(id: Int): Int

    @Query("SELECT * FROM favorite_user")
    fun getFavoriteUser(): LiveData<List<FavoriteUser>>

    @Query("DELETE FROM favorite_user WHERE id = :id")
    fun removeFromFavorite(id: Int)

    @Insert
    fun addToFavorite(user: FavoriteUser)

    @Query("SELECT * FROM favorite_user")
    fun findAll():Cursor
}
