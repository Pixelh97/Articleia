package com.example.repository.repository

import com.example.domain.repository.PostRepository
import com.example.entity.Comment
import com.example.entity.Post
import com.example.repository.dataSource.local.PostLocalDataSource
import com.example.repository.dataSource.remote.PostRemoteDataSource
import com.example.repository.mapper.toComment
import com.example.repository.mapper.toPost
import com.example.repository.mapper.toPosts
import com.example.repository.mapper.toPostsDto

class PostRepositoryImpl(
    private val localDataSource: PostLocalDataSource,
    private val remoteDataSource: PostRemoteDataSource,
) : PostRepository {
    override suspend fun fetchPosts(): List<Post> {
        val cachedPosts = localDataSource.fitchPosts()
        if (cachedPosts.isEmpty()) {
            val posts =
                remoteDataSource
                    .fetchPosts()
                    .map {
                        val comments = remoteDataSource.fetchCommentsByPostId(it.id)
                        it.toPost(comments.size)
                    }
            localDataSource.addAllPosts(posts.toPostsDto())
            return posts
        } else {
            return cachedPosts.toPosts()
        }
    }

    override suspend fun fetchCommentsByPostId(postId: Int): List<Comment> =
        remoteDataSource.fetchCommentsByPostId(postId).map {
            it.toComment()
        }
}
