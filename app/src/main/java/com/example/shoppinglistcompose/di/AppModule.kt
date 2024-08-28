package com.example.shoppinglistcompose.di

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import com.example.shoppinglistcompose.data.repository.ShoppingListRepositoryImpl
import com.example.shoppinglistcompose.data.service.CacheService
import com.example.shoppinglistcompose.data.service.CacheServiceImpl
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
    @Reusable
    @Binds
    fun bindRepository(impl: ShoppingListRepositoryImpl): ShoppingListRepository

    @Singleton
    @Binds
    fun bindService(impl: ShoppingListServiceImpl): ShoppingListService
}

@Module
@InstallIn(SingletonComponent::class)
class ProvideModule {
    @Singleton
    @Provides
    fun provideDatabaseHelper(@ApplicationContext context: Context): DatabaseHelper {
        return DatabaseHelper(context)
    }

    @Singleton
    @Provides
    fun provideImageLoader(@ApplicationContext context: Context): ImageLoader {
        return ImageLoader.Builder(context)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache{
                MemoryCache.Builder(context)
                    .maxSizePercent(0.25)
                    .strongReferencesEnabled(true)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.05)
                    .build()
            }
            .logger(DebugLogger())
            .respectCacheHeaders(false)
            .build()
    }

    @Reusable
    @Provides
    fun provideCacheService(@ApplicationContext context: Context): CacheService{
        return CacheServiceImpl(context)
    }
}