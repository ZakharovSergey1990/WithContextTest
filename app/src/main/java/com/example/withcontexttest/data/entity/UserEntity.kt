package com.example.withcontexttest.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val age: Int,
    val email: String,

    @Embedded(prefix = "address_")
    val address: AddressEntity,

    @Embedded(prefix = "education_")
    val education: EducationEntity
)

data class AddressEntity(
    val street: String,
    val city: String,
    val postalCode: String,
    val country: String
)

data class EducationEntity(
    val university: String,
    val degree: String,
    val graduationYear: Int
)