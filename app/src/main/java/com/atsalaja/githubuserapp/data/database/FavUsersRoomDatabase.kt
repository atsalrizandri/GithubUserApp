package com.atsalaja.githubuserapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavUsers::class], version = 1)
abstract class FavUsersRoomDatabase: RoomDatabase() {
    abstract fun favUsersDao(): FavUsersDao

    companion object {
        @Volatile
        private var INSTANCE: FavUsersRoomDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): FavUsersRoomDatabase {
            if (INSTANCE == null) {
                synchronized(FavUsersRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavUsersRoomDatabase::class.java, "favorite_users_database")
                        .build()
                }
            }
            return INSTANCE as FavUsersRoomDatabase
        }
    }
}