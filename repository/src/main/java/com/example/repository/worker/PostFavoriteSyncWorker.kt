package com.example.repository.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.repository.dataSource.local.PostLocalDataSource
import com.example.repository.dataSource.remote.PostRemoteDataSource

class PostFavoriteSyncWorker(
    context: Context,
    params: WorkerParameters,
    private val localDataSource: PostLocalDataSource,
    private val remoteDataSource: PostRemoteDataSource,
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return try {
            val pendingLikes = localDataSource.getAllPendingFavorites()

            if (pendingLikes.isEmpty()) {
                return Result.success()
            }

            pendingLikes.forEach { pendingLike ->

                try {
                    if (pendingLike.isFavorite) {
                        remoteDataSource.removePostFromFavorites(pendingLike.postId)
                    } else {
                        remoteDataSource.addPostToFavorites(pendingLike.postId)
                    }

                    localDataSource.removeFavoriteQueue(pendingLike.postId, pendingLike.isFavorite)
                } catch (e: Exception) {
                    Log.e("LikeSyncWorker", "Failed to sync like for $pendingLike", e)
                }
            }

            val remaining = localDataSource.getAllPendingFavorites()
            if (remaining.isEmpty()) {
                Result.success()
            } else {
                Result.retry()
            }
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
