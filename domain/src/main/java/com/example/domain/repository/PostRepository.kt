package com.example.domain.repository

import com.example.entity.Comment
import com.example.entity.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun fetchPosts(): List<Post>

    suspend fun fetchCommentsByPostId(postId: Int): List<Comment>
}
