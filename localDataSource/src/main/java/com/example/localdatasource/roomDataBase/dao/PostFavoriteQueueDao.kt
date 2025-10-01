package com.example.localdatasource.roomDataBase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.repository.dto.local.LocalFavoriteQueueDto

@Dao
interface PostFavoriteQueueDao {
    @Insert
    suspend fun insert(item: LocalFavoriteQueueDto)

    @Delete
    suspend fun delete(item: LocalFavoriteQueueDto)

    @Query("SELECT * FROM FavoriteQueue")
    suspend fun getAll(): List<LocalFavoriteQueueDto>
}
