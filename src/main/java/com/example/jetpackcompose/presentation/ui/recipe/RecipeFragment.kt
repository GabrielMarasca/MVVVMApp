package com.example.jetpackcompose.presentation.ui.recipe

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.jetpackcompose.presentation.BaseApplication
import com.example.jetpackcompose.presentation.components.RecipeView
import com.example.jetpackcompose.presentation.components.util.ShimmerCard
import com.example.jetpackcompose.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@AndroidEntryPoint
class RecipeFragment: Fragment() {
    @Inject
    lateinit var application: BaseApplication

    private val viewModel: RecipeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("recipeId")?.let {
                recipeId -> viewModel.onTriggerEvent(RecipeEvent.GetRecipeEvent(recipeId))
        }
    }
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val loading = viewModel.loading.value
                val recipe = viewModel.recipe.value
                AppTheme(
                    darkTheme = application.isDark.value,
                )
                {
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = MaterialTheme.colorScheme.background),
                        topBar = {},
                        content = {
                            if (loading && recipe == null) {
                                ShimmerCard(cardHeight = 300.dp, padding = 10.dp)
                            }
                            else recipe?.let {
                                RecipeView(
                                    recipe = recipe,
                                )
                            }
                        },
                    )
                }
            }
        }
    }
}