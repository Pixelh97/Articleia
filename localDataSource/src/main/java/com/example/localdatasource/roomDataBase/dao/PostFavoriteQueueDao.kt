package com.example.localdatasource.roomDataBase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.repository.dto.local.LocalFavoriteQueueDto

@Dao
interface PostFavoriteQueueDao {
    @Insert
    suspend fun insert(postId: LocalFavoriteQueueDto)

    @Delete
    suspend fun delete(postId: LocalFavoriteQueueDto)

    @Query("SELECT * FROM LocalFavoriteQueueDto")
    suspend fun getAll(): List<Int>
}
