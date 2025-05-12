package com.example.withcontexttest.data.repository

import android.util.Log
import com.example.withcontexttest.data.entity.UserEntity
import com.example.withcontexttest.data.toData
import com.example.withcontexttest.data.toDomain
import com.example.withcontexttest.domain.model.User
import com.example.withcontexttest.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryRuntime @Inject constructor() : UserRepository {

    var users = emptyList<UserEntity>()

    override suspend fun clear() {
        Log.i("UserRepositoryImpl", "clear")
        users = emptyList()
    }

    override suspend fun saveUsers(users: List<User>) {
        Log.i("UserRepositoryImpl", "saveUsers running on thread: ${Thread.currentThread().name}")
        this.users = users.map { it.toData() }
    }


    override suspend fun getAllUsers(): List<User> {
        Log.i("UserRepositoryImpl", "getAllUsers running on thread: ${Thread.currentThread().name}")
        return users.map { it.toDomain() }
    }
}