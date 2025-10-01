package com.example.repository.repository

import com.example.domain.repository.PostRepository
import com.example.entity.Comment
import com.example.entity.Post
import com.example.repository.dataSource.remote.PostRemoteDataSource
import com.example.repository.mapper.toComment
import com.example.repository.mapper.toPost

class PostRepositoryImpl(
    private val remoteDataSource: PostRemoteDataSource,
) : PostRepository {
    override suspend fun fetchPosts(): List<Post> =
        remoteDataSource
            .fetchPosts()
            .map {
                val comments = remoteDataSource.fetchCommentsByPostId(it.id)
                it.toPost(comments.size)
            }

    override suspend fun fetchCommentsByPostId(postId: Int): List<Comment> =
        remoteDataSource.fetchCommentsByPostId(postId).map {
            it.toComment()
        }
}
