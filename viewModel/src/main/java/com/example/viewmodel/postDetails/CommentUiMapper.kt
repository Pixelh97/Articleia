package com.example.viewmodel.postDetails

import com.example.entity.Comment

fun Comment.toCommentUiState() =
    PostDetailsUiState.PostDetailsCommentUiState(
        id = id,
        name = name,
        email = email,
        body = body,
    )

fun List<Comment>.toCommentUiStateList() = map { it.toCommentUiState() }