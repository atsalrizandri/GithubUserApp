package com.atsalaja.githubuserapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavUsersDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(userFav: FavUsers)

    @Query("SELECT * FROM FavUsers")
    fun getUsersFav(): LiveData<List<FavUsers>>

    @Query("SELECT * FROM FavUsers WHERE username = :username")
    fun getUsersFavByUsername(username: String): LiveData<FavUsers>

    @Query("DELETE FROM FavUsers WHERE username = :username")
    fun deleteUsersFav(username: String)
}