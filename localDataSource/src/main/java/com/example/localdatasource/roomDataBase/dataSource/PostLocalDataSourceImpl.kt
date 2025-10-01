package com.example.localdatasource.roomDataBase.dataSource

import com.example.localdatasource.roomDataBase.dao.PostFavoriteQueueDao
import com.example.localdatasource.roomDataBase.dao.PostsDao
import com.example.repository.dataSource.local.PostLocalDataSource
import com.example.repository.dto.local.LocalFavoriteQueueDto
import com.example.repository.dto.local.LocalPostDto

class PostLocalDataSourceImpl(
    private val postDao: PostsDao,
    private val postFavoriteQueueDao: PostFavoriteQueueDao,
) : PostLocalDataSource {
    override suspend fun addFavoriteQueue(
        postId: Int,
        isFavorite: Boolean,
    ) {
        postFavoriteQueueDao.insert(LocalFavoriteQueueDto(postId, isFavorite))
    }

    override suspend fun removeFavoriteQueue(
        postId: Int,
        isFavorite: Boolean,
    ) {
        postFavoriteQueueDao.delete(LocalFavoriteQueueDto(postId, isFavorite))
    }

    override suspend fun getAllPendingFavorites(): List<LocalFavoriteQueueDto> = postFavoriteQueueDao.getAll()

    override suspend fun addAllPosts(posts: List<LocalPostDto>) {
        postDao.insertAll(posts)
    }

    override suspend fun fitchPosts(): List<LocalPostDto> = postDao.getAllPosts()
}
