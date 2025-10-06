package com.example.viewmodel.posts

import com.example.entity.Post

fun Post.toPostUiState(): PostsScreenUiState.PostUiState =
    PostsScreenUiState.PostUiState(
        id = this.id,
        title = this.title,
        body = this.body,
        isFavorite = this.isFavorite,
    )

fun List<Post>.toPostUiState(): List<PostsScreenUiState.PostUiState> = this.map { it.toPostUiState() }
