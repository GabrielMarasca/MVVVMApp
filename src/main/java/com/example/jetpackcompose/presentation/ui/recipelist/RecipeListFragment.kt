@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.jetpackcompose.presentation.ui.recipelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.jetpackcompose.presentation.BaseApplication
import com.example.jetpackcompose.presentation.components.RecipeList
import com.example.jetpackcompose.presentation.components.SearchAppBar
import com.example.jetpackcompose.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment: Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel: RecipeListViewModel by viewModels()

    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalComposeUiApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val theme = application.isDark.value
                AppTheme(darkTheme = theme) {
                    val recipes = viewModel.recipes.value
                    val query = viewModel.query.value
                    val selectedCategory = viewModel.selectedCategory.value
                    val loading = viewModel.loading.value
                    val page = viewModel.page.value
                    Scaffold(
                        topBar = {
                            SearchAppBar(
                                query = query,
                                onQueryChanged = viewModel::onQueryChanged,
                                onExecuteSearch = { viewModel.onTriggerEvent(RecipeListEvent.NewSearchEvent) },
                                categories = getAllFoodCategories(),
                                selectedCategory = selectedCategory,
                                onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                                onToggleTheme = { application.toggleTheme() }
                            )
                        },
                        content = {
                            RecipeList(
                                loading = loading,
                                recipes = recipes,
                                page = page ,
                                onChangeRecipeScrollPosition = viewModel::onChangeRecipeScrollPosition,
                                nextPageEvent = {viewModel.onTriggerEvent(RecipeListEvent.NextPageEvent) },
                                navController = findNavController()
                            )
                        },
                    )
                }
            }
        }
    }
}