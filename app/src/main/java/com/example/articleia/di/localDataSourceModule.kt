package com.example.articleia.di

import com.example.localdatasource.roomDataBase.ArticleiaDataBase
import com.example.localdatasource.roomDataBase.dao.PostFavoriteQueueDao
import com.example.localdatasource.roomDataBase.dao.PostsDao
import com.example.localdatasource.roomDataBase.dataSource.PostLocalDataSourceImpl
import com.example.repository.dataSource.local.PostLocalDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val localDataSourceModule =
    module {
        single { ArticleiaDataBase.getInstance(androidApplication()) }
        single<PostsDao> { get<ArticleiaDataBase>().postDao() }
        single<PostFavoriteQueueDao> { get<ArticleiaDataBase>().postFavoriteQueueDao() }

        singleOf(::PostLocalDataSourceImpl) { bind<PostLocalDataSource>() }
    }
