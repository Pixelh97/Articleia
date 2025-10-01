package com.example.remotedatasource.dataSource

import com.example.domain.exceptions.NoInternetException
import com.example.remotedatasource.client.NetworkClient
import com.example.remotedatasource.utill.apiHandler.responseCall
import com.example.repository.dataSource.remote.PostRemoteDataSource
import com.example.repository.dto.remote.CommentDto
import com.example.repository.dto.remote.PostDto

class PostRemoteDataSourceImpl(
    private val networkClient: NetworkClient,
) : PostRemoteDataSource {
    override suspend fun fetchPosts(): List<PostDto> = responseCall { networkClient.get(GET_POSTS) }

    override suspend fun fetchCommentsByPostId(postId: Int): List<CommentDto> =
        responseCall { networkClient.get("$GET_POSTS/$postId$GET_COMMENTS_BY_POST_ID") }

    override suspend fun addPostToFavorites(postId: Int): Unit = throw NoInternetException()

    override suspend fun removePostFromFavorites(postId: Int): Unit = throw NoInternetException()

    companion object {
        private const val GET_POSTS = "/posts"
        private const val GET_COMMENTS_BY_POST_ID = "/comments"
    }
}
