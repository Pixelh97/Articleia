package com.example.repository.dataSource.remote

import com.example.repository.dataSource.dto.remote.CommentDto
import com.example.repository.dataSource.dto.remote.PostDto
import com.example.repository.dataSource.dto.remote.Response

interface PostRemoteDataSource {
    suspend fun fetchPosts(): Response<PostDto>

    suspend fun fetchCommentsByPostId(postId: Int): Response<CommentDto>
}
