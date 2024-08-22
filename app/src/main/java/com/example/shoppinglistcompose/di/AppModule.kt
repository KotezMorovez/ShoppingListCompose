package com.example.shoppinglistcompose.di

import android.content.Context
import com.example.shoppinglistcompose.data.repository.ShoppingListRepositoryImpl
import com.example.shoppinglistcompose.data.service.DatabaseHelper
import com.example.shoppinglistcompose.data.service.ShoppingListService
import com.example.shoppinglistcompose.data.service.ShoppingListServiceImpl
import com.example.shoppinglistcompose.domain.ShoppingListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BindModule {
    @Binds
    @Reusable
    fun bindRepository(impl: ShoppingListRepositoryImpl): ShoppingListRepository

    @Binds
    @Reusable
    fun bindService(impl: ShoppingListServiceImpl): ShoppingListService
}

@Module
@InstallIn(SingletonComponent::class)
class ProvideModule {
    @Reusable
    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Singleton
    @Provides
    fun provideDatabaseHelper(@ApplicationContext context: Context): DatabaseHelper {
        return DatabaseHelper(context)
    }
}