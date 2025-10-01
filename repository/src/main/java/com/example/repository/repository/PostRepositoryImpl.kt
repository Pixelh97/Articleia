package com.example.repository.repository

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.domain.repository.PostRepository
import com.example.entity.Comment
import com.example.entity.Post
import com.example.repository.dataSource.local.PostLocalDataSource
import com.example.repository.dataSource.remote.PostRemoteDataSource
import com.example.repository.mapper.toComment
import com.example.repository.mapper.toPost
import com.example.repository.mapper.toPosts
import com.example.repository.mapper.toPostsDto
import com.example.repository.worker.PostFavoriteSyncWorker
import java.util.concurrent.TimeUnit

class PostRepositoryImpl(
    private val localDataSource: PostLocalDataSource,
    private val remoteDataSource: PostRemoteDataSource,
    private val context: Context,
) : PostRepository {
    override suspend fun fetchPosts(): List<Post> {
        val cachedPosts = localDataSource.fitchPosts()
        if (cachedPosts.isEmpty()) {
            val posts =
                remoteDataSource
                    .fetchPosts()
                    .map {
                        val comments = remoteDataSource.fetchCommentsByPostId(it.id)
                        it.toPost(comments.size, isPostInPendingQueue(it.id))
                    }
            localDataSource.addAllPosts(posts.toPostsDto())
            return posts
        } else {
            return cachedPosts.toPosts()
        }
    }

    private suspend fun isPostInPendingQueue(id: Int): Boolean {
        val pendingFavorites = localDataSource.getAllPendingFavorites()
        return pendingFavorites.find { it.postId == id }?.isFavorite ?: false
    }

    override suspend fun fetchCommentsByPostId(postId: Int): List<Comment> =
        remoteDataSource.fetchCommentsByPostId(postId).map {
            it.toComment()
        }

    override suspend fun togglePostFavorite(
        postId: Int,
        isFavorite: Boolean,
    ) {
        updateCachedPost(postId, isFavorite)
        try {
            if (isFavorite) {
                remoteDataSource.addPostToFavorites(postId)
            } else {
                remoteDataSource.removePostFromFavorites(postId)
            }
        } catch (e: Exception) {
            addToPendingQueue(postId, isFavorite = isFavorite)
        }
    }

    private suspend fun updateCachedPost(
        postId: Int,
        isFavorite: Boolean,
    ) {
        val updatedPost =
            localDataSource
                .getPostById(postId)
                ?.copy(isFavorite = isFavorite)
        if (updatedPost != null) {
            localDataSource.updatePost(updatedPost)
        }
    }

    private suspend fun addToPendingQueue(
        postId: Int,
        isFavorite: Boolean,
    ) {
        if (isAlreadyInQueue(postId)) {
            localDataSource.removeFavoriteQueue(postId, !isFavorite)
        } else {
            localDataSource.addFavoriteQueue(postId, isFavorite)
            enqueueSyncWork()
        }
    }

    private suspend fun isAlreadyInQueue(postId: Int): Boolean {
        val pendingFavorites = localDataSource.getAllPendingFavorites()
        return pendingFavorites.any { it.postId == postId }
    }

    private fun enqueueSyncWork() {
        val constraints =
            Constraints
                .Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        val syncRequest =
            OneTimeWorkRequestBuilder<PostFavoriteSyncWorker>()
                .setConstraints(constraints)
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    WorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS,
                ).build()

        WorkManager
            .getInstance(context)
            .enqueueUniqueWork(
                SYNC_WORK_NAME,
                ExistingWorkPolicy.KEEP,
                syncRequest,
            )
    }

    companion object {
        private const val SYNC_WORK_NAME = "PostFavoriteSyncWork"
    }
}
