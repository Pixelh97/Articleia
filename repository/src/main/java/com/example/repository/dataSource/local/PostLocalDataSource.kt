package com.example.repository.dataSource.local

import com.example.repository.dto.local.LocalFavoriteQueueDto
import com.example.repository.dto.local.LocalPostDto

interface PostLocalDataSource {
    suspend fun addFavoriteQueue(
        postId: Int,
        isFavorite: Boolean,
    )

    suspend fun removeFavoriteQueue(
        postId: Int,
        isFavorite: Boolean,
    )

    suspend fun getAllPendingFavorites(): List<LocalFavoriteQueueDto>

    suspend fun addAllPosts(posts: List<LocalPostDto>)

    suspend fun updatePost(post: LocalPostDto)

    suspend fun fitchPosts(): List<LocalPostDto>

    suspend fun getPostById(postId: Int): LocalPostDto?
}
