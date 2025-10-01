package com.example.viewmodel.postDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostDetailsViewModel(
    private val postRepository: PostRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(PostDetailsUiState())
    val state = _state.asStateFlow()

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
                _state.update {
                    it.copy(
                        comments = comments,
                        isLoading = false,
                        error = null,
                    )
                }
            } catch (e: Exception) {
                _state.emit(
                    _state.value.copy(
                        isLoading = false,
                        error = e.message,
                    ),
                )
            }
        }
    }

    fun toggleFavorite(isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                postRepository.togglePostFavorite(_state.value.postUiState.id, isFavorite)
                _state.update {
                    it.copy(
                        postUiState = it.postUiState.copy(isFavorite = isFavorite),
                        error = null,
                    )
                }
            } catch (e: Exception) {
//                _state.emit(
//                    _state.value.copy(
//                        error = e.message,
//                    )
//                )
            }
        }
    }
}
