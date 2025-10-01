package com.example.viewmodel.posts

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PostsViewModel : ViewModel() {
    private val _state = MutableStateFlow(PostsScreenUiState())
    val state = _state.asStateFlow()

    fun onPostClick(postId: Int) {
    }

    fun onTabSelected(tabIndex: Int) {
        _state.update {
            it.copy(currentSelectedTabIndex = tabIndex)
        }
    }

    fun retryFetchingPosts() {
    }
}
