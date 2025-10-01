package com.example.articleia.di

import com.example.remotedatasource.client.KtorClient
import com.example.remotedatasource.client.NetworkClient
import com.example.remotedatasource.dataSource.PostRemoteDataSourceImpl
import com.example.repository.dataSource.remote.PostRemoteDataSource
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataSourceModule =
    module {
        single {
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }
        }

        singleOf(::KtorClient) { bind<NetworkClient>() }
        singleOf(::PostRemoteDataSourceImpl) { bind<PostRemoteDataSource>() }
    }
