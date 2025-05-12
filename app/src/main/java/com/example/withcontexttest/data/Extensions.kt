package com.example.withcontexttest.data

import com.example.withcontexttest.data.entity.AddressEntity
import com.example.withcontexttest.data.entity.EducationEntity
import com.example.withcontexttest.data.entity.UserEntity
import com.example.withcontexttest.domain.model.Address
import com.example.withcontexttest.domain.model.Education
import com.example.withcontexttest.domain.model.User

fun UserEntity.toDomain(): User {
    return User(
        id = id,
        name = name,
        email = email,
        age = age,
        address = Address(
            street = address.street,
            city = address.city,
            country = address.country,
            postalCode = address.postalCode
        ),
        education = Education(
            university = education.university,
            degree = education.degree,
            graduationYear = education.graduationYear
        )
    )
}

fun User.toData(): UserEntity {
    return UserEntity(
        id = id,
        name = name,
        email = email,
        age = age,
        address = AddressEntity(
            street = address.street,
            city = address.city,
            country = address.country,
            postalCode = address.postalCode
        ),
        education = EducationEntity(
            university = education.university,
            degree = education.degree,
            graduationYear = education.graduationYear
        )
    )
}