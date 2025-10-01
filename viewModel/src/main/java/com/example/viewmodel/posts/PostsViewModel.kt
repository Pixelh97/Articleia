package com.example.viewmodel.posts

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PostsViewModel :
    ViewModel(),
    PostsInteractionListener {
    private val _state = MutableStateFlow(PostsScreenUiState())
    val state = _state.asStateFlow()

    override fun onPostClick(postId: Int) {
    }

    override fun onTabSelected(tabIndex: Int) {
    }
}
