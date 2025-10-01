package com.example.repository.mapper

import com.example.entity.Post
import com.example.repository.dto.local.LocalPostDto
import com.example.repository.dto.remote.PostDto

fun PostDto.toPost(commentsCounter: Int) =
    Post(
        id = id,
        userId = userId,
        title = title,
        body = body,
        commentsCounter = commentsCounter,
    )

fun Post.toLocalPostDto() =
    LocalPostDto(
        id = id,
        userId = userId,
        title = title,
        body = body,
        commentsCounter = commentsCounter,
    )

fun List<Post>.toPostsDto() = map { it.toLocalPostDto() }

fun LocalPostDto.toPost() =
    Post(
        id = id,
        userId = userId,
        title = title,
        body = body,
        commentsCounter = commentsCounter,
    )

fun List<LocalPostDto>.toPosts() = map { it.toPost() }
