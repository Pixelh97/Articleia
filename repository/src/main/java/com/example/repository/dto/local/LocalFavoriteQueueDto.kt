package com.example.repository.dto.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteQueue")
data class LocalFavoriteQueueDto(
    @PrimaryKey val postId: Int,
    val isFavorite: Boolean,
)
