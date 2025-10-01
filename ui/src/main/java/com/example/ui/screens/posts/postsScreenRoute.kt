package com.example.ui.screens.posts

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.ui.navigation.Route

fun NavGraphBuilder.postsScreenRoute(navController: NavController) {
    composable<Route.PostList> {
        PostsScreen(navController = navController)
    }
}
