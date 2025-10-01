package com.example.repository.dto.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalPostDto(
    @PrimaryKey val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
    val commentsCounter: Int,
)
