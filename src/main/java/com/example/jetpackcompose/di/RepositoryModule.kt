package com.example.jetpackcompose.di

import com.example.jetpackcompose.network.RetrofitService
import com.example.jetpackcompose.network.model.RecipeDtoMapper
import com.example.jetpackcompose.repository.RecipeRepository
import com.example.jetpackcompose.repository.RecipeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(
        retrofitService: RetrofitService,
        recipeMapper: RecipeDtoMapper
    ): RecipeRepository {
        return  RecipeRepositoryImpl(
            retrofitService = retrofitService,
            mapper = recipeMapper
        )
    }
}