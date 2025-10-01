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
    override suspend fun addFavorite(postId: Int) {
        postFavoriteQueueDao.insert(LocalFavoriteQueueDto(postId))
    }

    override suspend fun removeFavorite(postId: Int) {
        postFavoriteQueueDao.delete(LocalFavoriteQueueDto(postId))
    }

    override suspend fun getAllFavorites(): List<Int> = postFavoriteQueueDao.getAll()

    override suspend fun addAllPosts(posts: List<LocalPostDto>) {
        postDao.insertAll(posts)
    }

    override suspend fun fitchPosts(): List<LocalPostDto> = postDao.getAllPosts()
}
