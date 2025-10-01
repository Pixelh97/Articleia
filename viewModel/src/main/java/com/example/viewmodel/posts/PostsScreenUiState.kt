package com.example.viewmodel.posts

data class PostsScreenUiState(
    val posts: List<PostUiState> = emptyList(),
    val currentSelectedTabIndex: Int = 0,
    val isLoading: Boolean = false,
    val isNoInternetConnection: Boolean = false,
) {
    data class PostUiState(
        val id: Int,
        val title: String,
        val body: String,
        val commentsCount: Int,
    )
}
