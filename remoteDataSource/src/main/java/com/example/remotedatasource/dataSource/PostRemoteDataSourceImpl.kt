package com.example.remotedatasource.dataSource

import com.example.remotedatasource.client.NetworkClient
import com.example.remotedatasource.utill.apiHandler.responseCall
import com.example.repository.dataSource.dto.remote.CommentDto
import com.example.repository.dataSource.dto.remote.PostDto
import com.example.repository.dataSource.remote.PostRemoteDataSource

class PostRemoteDataSourceImpl(
    private val networkClient: NetworkClient,
) : PostRemoteDataSource {
    override suspend fun fetchPosts(): List<PostDto> = responseCall { networkClient.get(GET_POSTS) }

    override suspend fun fetchCommentsByPostId(postId: Int): List<CommentDto> =
        responseCall { networkClient.get("$GET_POSTS/$postId$GET_COMMENTS_BY_POST_ID") }

    companion object {
        private const val GET_POSTS = "/posts"
        private const val GET_COMMENTS_BY_POST_ID = "/comments"
    }
}
