package com.example.articleia.di

import com.example.domain.repository.PostRepository
import com.example.repository.repository.PostRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule =
    module {
        singleOf(::PostRepositoryImpl) { bind<PostRepository>() }
    }
