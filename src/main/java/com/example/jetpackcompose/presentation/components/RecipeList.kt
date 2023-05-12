package com.example.jetpackcompose.presentation.components

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetpackcompose.R
import com.example.jetpackcompose.domain.model.Recipe
import com.example.jetpackcompose.presentation.components.util.ShimmerList
import com.example.jetpackcompose.presentation.ui.recipelist.PAGE_SIZE
import com.example.jetpackcompose.presentation.ui.recipelist.RecipeListEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun RecipeList(
    loading: Boolean,
    recipes: List<Recipe>,
    page: Int,
    onChangeRecipeScrollPosition: (Int) -> Unit,
    nextPageEvent: (RecipeListEvent) -> Unit,
    navController: NavController,
){
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 120.dp)
    ) {
        if (loading && recipes.isEmpty()) {
            ShimmerList(280.dp, 4.dp)
        } else {
            LazyColumn {
                itemsIndexed(
                    items = recipes
                ) { index, recipe ->
                    onChangeRecipeScrollPosition(index)
                    if (index+1 >= (page * PAGE_SIZE) && !loading)
                    {
                        nextPageEvent(RecipeListEvent.NextPageEvent)
                    }
                    RecipeCard(
                        recipe = recipe,
                        onClick = {
                            if (recipe.id != null){
                                val bundle = Bundle()
                                bundle.putInt("recipeId", recipe.id)
                                navController.navigate(R.id.viewRecipe,bundle)
                            }
                    })
                }
            }
        }
        CircularIndeterminateProgressBar(
            isDisplayed = loading,
            verticalBias = 0.3f
        )
    }

}