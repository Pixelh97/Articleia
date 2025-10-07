package com.example.viewmodel.postDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.exceptions.ArticleiaException
import com.example.domain.repository.PostRepository
import com.example.viewmodel.posts.PostsScreenUiState
import com.example.viewmodel.posts.toPostUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostDetailsViewModel(
    private val postRepository: PostRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _state = MutableStateFlow(PostDetailsUiState())
    val state = _state.asStateFlow()

    val postId: Int = savedStateHandle["postId"] ?: 0

    init {
        fitchPostComments()
    }

    private fun fitchPostComments() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val comments =
                    postRepository
                        .fetchCommentsByPostId(1)
                        .toCommentUiStateList()
                val post = postRepository.fetchPostById(postId)
                _state.update {
                    it.copy(
                        postUiState = post.toPostUiState(),
                        comments = comments,
                        isLoading = false,
                        isError = false,
                    )
                }
            } catch (e: ArticleiaException) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        isError = true,
                    )
                }
            }
        }
    }

    fun toggleFavorite(isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            postRepository.togglePostFavorite(_state.value.postUiState.id, isFavorite)
            _state.update {
                it.copy(
                    postUiState = it.postUiState.copy(isFavorite = isFavorite),
                    isError = false,
                )
            }
        }
    }

    fun retryFetchingData() {
        fitchPostComments()
    }
}
