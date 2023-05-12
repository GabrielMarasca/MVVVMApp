package com.example.jetpackcompose.presentation.ui.recipelist

sealed class RecipeListEvent {
    object NewSearchEvent: RecipeListEvent()

    object NextPageEvent: RecipeListEvent()

    object RestoreStateEvent: RecipeListEvent()
}