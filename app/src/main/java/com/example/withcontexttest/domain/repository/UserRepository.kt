package com.example.withcontexttest.domain.repository

import com.example.withcontexttest.domain.model.User

interface UserRepository {
    suspend fun clear()
    suspend fun saveUsers(users: List<User>)
    suspend fun getAllUsers(): List<User>
}