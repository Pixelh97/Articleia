package com.example.localdatasource.roomDataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.repository.dto.local.LocalPostDto

@Dao
interface PostsDao {
    @Insert
    suspend fun insertAll(posts: List<LocalPostDto>)

    @Query("SELECT * FROM LocalPostDto")
    suspend fun getAllPosts(): List<LocalPostDto>
}
