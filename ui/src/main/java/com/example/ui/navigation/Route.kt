package com.example.ui.navigation

import com.example.viewmodel.posts.PostsScreenUiState
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    object PostList : Route

    @Serializable
    data class PostDetails(
        val postId: Int,
        val postTitle: String,
        val postBody: String,
    ) : Route
}
