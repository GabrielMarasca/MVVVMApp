package com.example.jetpackcompose.presentation.ui.recipe

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose.domain.model.Recipe
import com.example.jetpackcompose.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val STATE_KEY_RECIPE = "state.key.recipeId"

@HiltViewModel
class RecipeViewModel
@Inject
constructor(
    private val recipeRepository: RecipeRepository,
    @Named ("auth_token") private val token: String,
    private val savedStateHandle: SavedStateHandle,
):ViewModel(){
    val recipe: MutableState<Recipe?> = mutableStateOf(null)
    val loading = mutableStateOf(false)

    init {
        savedStateHandle.get<Int>(STATE_KEY_RECIPE)?.let {
            recipeId -> onTriggerEvent(RecipeEvent.GetRecipeEvent(recipeId))
        }
    }

    fun onTriggerEvent(event: RecipeEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is RecipeEvent.GetRecipeEvent -> {
                        if (recipe.value==null) {
                            getRecipe(event.id)
                        }
                    }
                }

            } catch (e: java.lang.Exception){
                Log.e(TAG, "onTriggerEvent: $e, ${e.cause}")
            }
        }
    }

    private suspend fun getRecipe(id: Int){
        loading.value = true

        val recipe = recipeRepository.get(token = token, id = id)
        this.recipe.value = recipe

        savedStateHandle[STATE_KEY_RECIPE] = recipe.id
        loading.value = false
    }
}