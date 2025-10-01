package com.example.repository.dataSource.local

import com.example.entity.Post
import com.example.repository.dto.local.LocalPostDto

interface PostLocalDataSource {
    suspend fun addFavorite(postId: Int)

    suspend fun removeFavorite(postId: Int)

    suspend fun getAllFavorites(): List<Int>

    suspend fun addAllPosts(posts: List<LocalPostDto>)

    suspend fun fitchPosts(): List<LocalPostDto>
}
