package com.example.viewmodel.postDetails

import com.example.viewmodel.posts.PostsScreenUiState

data class PostDetailsUiState(
    val postUiState: PostsScreenUiState.PostUiState = PostsScreenUiState.PostUiState(),
    val comments: List<PostDetailsCommentUiState> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
) {
    data class PostDetailsCommentUiState(
        val id: Int = 0,
        val name: String = "",
        val email: String = "",
        val body: String = "",
    )
}
