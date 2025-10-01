package com.example.localdatasource.roomDataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.repository.dto.local.LocalPostDto

@Dao
interface PostsDao {
    @Insert
    suspend fun insertAll(posts: List<LocalPostDto>)

    @Upsert
    suspend fun upsert(post: LocalPostDto)

    @Query("SELECT * FROM LocalPostDto WHERE id = :postId")
    suspend fun getPostById(postId: Int): LocalPostDto?

    @Query("SELECT * FROM LocalPostDto")
    suspend fun getAllPosts(): List<LocalPostDto>
}
