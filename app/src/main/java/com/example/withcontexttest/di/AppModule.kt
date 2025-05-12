package com.example.withcontexttest.di

import android.content.Context
import androidx.room.Room
import com.example.withcontexttest.data.database.AppDatabase
import com.example.withcontexttest.data.database.UserDao
import com.example.withcontexttest.data.repository.UserRepositoryRuntime
import com.example.withcontexttest.domain.repository.UserRepository
import com.example.withcontexttest.domain.usecase.GetOldestUserUseCase
import com.example.withcontexttest.domain.usecase.SaveUsersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ProvideModule {

    @Singleton
    @Provides
    fun provideRuntimeRepository(
    ): UserRepository {
        return UserRepositoryRuntime()
    }

//    @Singleton
//    @Provides
//    fun provideRepository(
//        userDao: UserDao,
//    ): UserRepository {
//        return UserRepositoryPersistence(userDao)
//    }

    @Provides
    fun provideGetUsersUseCase(
        repository: UserRepository,
    ): GetOldestUserUseCase {
        return GetOldestUserUseCase(repository)
    }

    @Provides
    fun provideSaveUsersUseCase(
        repository: UserRepository,
    ): SaveUsersUseCase {
        return SaveUsersUseCase(repository)
    }

    @Provides
    fun provideDao(
        @ApplicationContext context: Context,
    ): UserDao {
        val database = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "database"
        ).build()
        return database.userDao()
    }
}

