package com.example.withcontexttest.domain.model

data class User(
    val id: Long,
    val name: String,
    val age: Int,
    val email: String,
    val address: Address,
    val education: Education
)

data class Address(
    val street: String,
    val city: String,
    val postalCode: String,
    val country: String
)

data class Education(
    val university: String,
    val degree: String,
    val graduationYear: Int
)