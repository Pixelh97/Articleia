package com.example.repository.mapper

import com.example.entity.Post
import com.example.repository.dto.remote.PostDto

fun PostDto.toPost(commentsCounter: Int) =
    Post(
        id = id,
        userId = userId,
        title = title,
        body = body,
        commentsCounter = commentsCounter,
    )
