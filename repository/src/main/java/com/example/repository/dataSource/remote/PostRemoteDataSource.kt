package com.example.repository.dataSource.remote

import com.example.repository.dto.remote.CommentDto
import com.example.repository.dto.remote.PostDto

interface PostRemoteDataSource {
    suspend fun fetchPosts(): List<PostDto>

    suspend fun fetchCommentsByPostId(postId: Int): List<CommentDto>
}
