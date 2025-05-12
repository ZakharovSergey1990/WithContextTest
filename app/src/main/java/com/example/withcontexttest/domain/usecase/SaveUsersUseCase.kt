package com.example.withcontexttest.domain.usecase

import com.example.withcontexttest.domain.model.Address
import com.example.withcontexttest.domain.model.Education
import com.example.withcontexttest.domain.model.User
import com.example.withcontexttest.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveUsersUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend fun execute(count: Int, dispatcher: CoroutineDispatcher?) {
        return if (dispatcher != null) {
            withContext(dispatcher) {
                userRepository.clear()
                userRepository.saveUsers(generateFakeUsers(count))
            }
        } else {
            userRepository.clear()
            userRepository.saveUsers(generateFakeUsers(count))
        }
    }

    private fun generateFakeUsers(count: Int): List<User> {
        return List(count) { index ->
            User(
                id = index.toLong(),
                name = "User $index",
                age = (18..60).random(),
                email = "user$index@example.com",
                address = Address(
                    street = "Street ${index % 100}",
                    city = "City ${index % 10}",
                    postalCode = "100${index % 50}",
                    country = "Country ${index % 5}"
                ),
                education = Education(
                    university = "University ${index % 20}",
                    degree = "Degree ${(index % 3) + 1}",
                    graduationYear = 2000 + (index % 20)
                )
            )
        }
    }
}