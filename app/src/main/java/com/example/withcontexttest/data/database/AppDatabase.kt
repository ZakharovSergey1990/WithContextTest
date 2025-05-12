package com.example.withcontexttest.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.withcontexttest.data.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}