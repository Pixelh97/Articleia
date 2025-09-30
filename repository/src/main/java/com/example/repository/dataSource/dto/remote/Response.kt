package com.example.repository.dataSource.dto.remote

import kotlinx.serialization.Serializable

@Serializable
data class Response<T>(
    val data: List<T>,
)
