package com.example.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.ui.screens.postDetails.postsDetailsScreenRoute
import com.example.ui.screens.posts.postsScreenRoute

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: Any = Route.PostList,
) {
    NavHost(navController = navController, startDestination = startDestination) {
        postsScreenRoute(navController)
        postsDetailsScreenRoute(navController)
    }
}
