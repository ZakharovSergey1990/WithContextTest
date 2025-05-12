package com.example.withcontexttest.domain.usecase

import com.example.withcontexttest.domain.model.User
import com.example.withcontexttest.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetOldestUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend fun execute(dispatcher: CoroutineDispatcher? = null): User {
        return if (dispatcher != null) {
            withContext(dispatcher) {
                userRepository.getAllUsers().maxBy { it.age }
            }
        } else {
            userRepository.getAllUsers().maxBy { it.age }
        }
    }
}