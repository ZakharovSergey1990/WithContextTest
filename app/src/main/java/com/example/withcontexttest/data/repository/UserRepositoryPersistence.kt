package com.example.withcontexttest.data.repository

import android.util.Log
import com.example.withcontexttest.data.database.UserDao
import com.example.withcontexttest.data.toData
import com.example.withcontexttest.data.toDomain
import com.example.withcontexttest.domain.model.User
import com.example.withcontexttest.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryPersistence @Inject constructor(
    private val userDao: UserDao,
) : UserRepository {

    override suspend fun clear() {
        Log.i("UserRepositoryImpl", "clear")
        userDao.clearAll()
    }

    override suspend fun saveUsers(users: List<User>) {
        Log.i("UserRepositoryImpl", "saveUsers running on thread: ${Thread.currentThread().name}")
        userDao.insertAll(users.map { it.toData() })
    }

    override suspend fun getAllUsers(): List<User> {
        Log.i("UserRepositoryImpl", "getAllUsers running on thread: ${Thread.currentThread().name}")
        val users = userDao.getAll()
        Log.i("UserRepositoryImpl", "getAllUsers: size = ${users.size}")
        return users.map { it.toDomain() }
    }
}