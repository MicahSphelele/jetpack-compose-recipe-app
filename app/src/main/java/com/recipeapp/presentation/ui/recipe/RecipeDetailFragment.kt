package com.recipeapp.presentation.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.recipeapp.domain.model.Recipe
import com.recipeapp.presentation.BaseApp
import com.recipeapp.presentation.theme.AppTheme
import com.recipeapp.util.AppConstants
import com.recipeapp.util.AppLogger
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeDetailFragment : Fragment() {

    private val viewModel by viewModels<RecipeDetailViewModel>()

    @Inject
    lateinit var application: BaseApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            val recipeID = bundle.getInt(AppConstants.KEY_RECIPE_ID)
            viewModel.onTriggeredEvent(RecipeEvent.GetDetailedRecipeEvent(recipeID))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply { 
            setContent {

                val loading = viewModel.loading.value
                val recipe = viewModel.recipe.value

                AppTheme(darkTheme = application.isDarkTheme.value) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colors.surface)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center) {
                            Text(text = recipe?.let {
                                "Selected Recipe : ${recipe.title}"
                            }?: "Loading...",
                                style = TextStyle(color = MaterialTheme.colors.onSecondary,
                                    fontSize = TextUnit.Sp(21)),
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                    }

                }
            }
        }
    }
}