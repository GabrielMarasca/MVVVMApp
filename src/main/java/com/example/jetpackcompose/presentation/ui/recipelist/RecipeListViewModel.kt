package com.example.jetpackcompose.presentation.ui.recipelist

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.jetpackcompose.domain.model.Recipe
import com.example.jetpackcompose.repository.RecipeRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val PAGE_SIZE = 30

const val STATE_KEY_PAGE = "recipe.state.page.key"
const val STATE_KEY_QUERY = "recipe.state.query.key"
const val STATE_KEY_LIST_POSITION = "recipe.state.query.list_position"
const val STATE_KEY_SELECTED_CATEGORY = "recipe.state.query.selected_category"
@HiltViewModel
class RecipeListViewModel
@Inject
constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {
    val recipes : MutableState<List<Recipe>> = mutableStateOf(ArrayList())
    val query = mutableStateOf("")
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    var loading = mutableStateOf( false)
    var isDark = mutableStateOf(false)
    val page = mutableStateOf(1)
    private var recipeListScrollPosition = 0
    init {
        savedStateHandle.get<Int>(STATE_KEY_PAGE)?.let { p -> setPage(p) }
        savedStateHandle.get<String>(STATE_KEY_QUERY)?.let { q -> setQuery(q) }
        savedStateHandle.get<Int>(STATE_KEY_LIST_POSITION)?.let { p -> setListScrollPosition(p) }
        savedStateHandle.get<FoodCategory>(STATE_KEY_SELECTED_CATEGORY)?.let { c -> setSelectedCategory(c) }
        if (recipeListScrollPosition != 0){
            onTriggerEvent(RecipeListEvent.RestoreStateEvent)
        } else {
            onTriggerEvent(RecipeListEvent.NewSearchEvent)
        }
    }

    fun onTriggerEvent(event: RecipeListEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is RecipeListEvent.NewSearchEvent -> {
                        newSearch()
                    }
                    is RecipeListEvent.NextPageEvent -> {
                        nextPage()
                    }
                    is RecipeListEvent.RestoreStateEvent -> {
                        restoreState()
                    }
                }
            }catch (e: Exception){
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
            finally {
                Log.d(TAG, "launchJob: finally called.")
            }
        }
    }

    private suspend fun restoreState(){
        loading.value = true
        val results: MutableList<Recipe> = mutableListOf()
        for (p in 1..page.value)
        {
            val result = repository.search(
                token = token,
                page = p,
                query = query.value
            )
            results.addAll(result)
            if(p == page.value)
            {
                recipes.value =results
                loading.value = false
            }
        }
    }

    private suspend fun newSearch() {
        loading.value = true
        resetSearchState()
        val result = repository.search(
            token = token,
            page = 1,
            query = query.value
        )
        recipes.value = result
        loading.value = false
    }

    private fun incPage(){
        setPage(page.value+1)
    }

    fun onChangeRecipeScrollPosition(position: Int){
        setListScrollPosition(position = position)
    }

    private suspend fun nextPage(){
        if (recipeListScrollPosition +1 >= (page.value * PAGE_SIZE))
        {
            loading.value = true
            incPage()
            if (page.value > 1)
            {
               val result = repository.search(
               token = token,
               page = page.value,
               query = query.value
               )
               appendRecipes(result)
            }
            loading.value = false
        }
    }
    private fun appendRecipes(recipes: List<Recipe>){
        val current = ArrayList(this.recipes.value)
        current.addAll(recipes)
        this.recipes.value = current
    }

    fun onQueryChanged(query: String){
        setQuery(query)
    }

    fun onSelectedCategoryChanged(category: String){
        val newCategory = getFoodCategory(category)
        setSelectedCategory(newCategory)
        onQueryChanged(category)
    }

    private fun resetSearchState(){
        recipes.value = listOf()
        page.value = 1
        onChangeRecipeScrollPosition(0)
        if(selectedCategory.value?.value != query.value)
            clearSelectedCategory()
    }

    private fun clearSelectedCategory(){
        setSelectedCategory(null)
    }

    private fun setListScrollPosition(position: Int){
        recipeListScrollPosition = position
        savedStateHandle[STATE_KEY_LIST_POSITION] = position
    }


    private fun setPage(page: Int){
        this.page.value = page
        savedStateHandle[STATE_KEY_PAGE] = page
    }

    private fun setSelectedCategory(category: FoodCategory?){
        selectedCategory.value = category
        savedStateHandle[STATE_KEY_SELECTED_CATEGORY] = category
    }

    private fun setQuery(query: String){
        this.query.value = query
        savedStateHandle[STATE_KEY_QUERY] = query
    }
}