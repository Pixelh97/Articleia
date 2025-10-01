package com.example.articleia.di

import com.example.viewmodel.postDetails.PostDetailsViewModel
import com.example.viewmodel.posts.PostsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule =
    module {
        viewModelOf(::PostsViewModel)
        viewModelOf(::PostDetailsViewModel)
    }
