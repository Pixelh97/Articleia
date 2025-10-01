package com.example.viewmodel.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.exceptions.ArticleiaException
import com.example.domain.repository.PostRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostsViewModel(
    private val postsRepository: PostRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(PostsScreenUiState())
    val state = _state.asStateFlow()

    init {
        fitchData()
    }

    private fun fitchData() {
        _state.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val posts = postsRepository.fetchPosts()
                _state.update {
                    it.copy(
                        isLoading = false,
                        isNoInternetConnection = false,
                        posts = posts.toPostUiState(),
                    )
                }
            } catch (e: ArticleiaException) {
                handleException(e)
            }
        }
    }

    private fun handleException(e: ArticleiaException) {
        _state.update {
            it.copy(
                isLoading = false,
                isNoInternetConnection = true,
            )
        }
    }

    fun onTabSelected(tabIndex: Int) {
        _state.update {
            it.copy(currentSelectedTabIndex = tabIndex)
        }
    }

    fun retryFetchingPosts() {
        fitchData()
    }
}
