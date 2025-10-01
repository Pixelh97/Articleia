package com.example.viewmodel.posts

data class PostsScreenUiState(
    val posts: List<PostUiState> = emptyList(),
    val isLoading: Boolean = false,
    val currentSelectedTabIndex: Int = 0
) {
    data class PostUiState(
        val id: Int,
        val title: String,
        val body: String,
        val commentsCount: Int,
    )
}
