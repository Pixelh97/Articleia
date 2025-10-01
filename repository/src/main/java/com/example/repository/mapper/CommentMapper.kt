package com.example.repository.mapper

import com.example.entity.Comment
import com.example.repository.dto.remote.CommentDto

fun CommentDto.toComment() =
    Comment(
        id = id,
        postId = postId,
        name = name,
        email = email,
        body = body,
    )
