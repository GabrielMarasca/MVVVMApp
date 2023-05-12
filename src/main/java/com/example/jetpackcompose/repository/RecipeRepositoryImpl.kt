package com.example.jetpackcompose.repository

import com.example.jetpackcompose.domain.model.Recipe
import com.example.jetpackcompose.network.RetrofitService
import com.example.jetpackcompose.network.model.RecipeDtoMapper

class RecipeRepositoryImpl(
    private val retrofitService: RetrofitService,
    private val mapper: RecipeDtoMapper
): RecipeRepository {
    override suspend fun search(token: String, page: Int, query: String): List<Recipe> {
        return mapper.toDomainList(retrofitService.search(token, page, query).recipes)
    }

    override suspend fun get(token: String, id: Int): Recipe {
        return mapper.mapToDomainModel(retrofitService.get(token = token, id))
    }
}