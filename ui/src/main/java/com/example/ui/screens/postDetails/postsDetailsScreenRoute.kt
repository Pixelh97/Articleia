package com.example.ui.screens.postDetails

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.ui.navigation.Route

fun NavGraphBuilder.postsDetailsScreenRoute(navController: NavController) {
    composable<Route.PostDetails> {
        PostDetailsScreen(navController = navController)
    }
}
