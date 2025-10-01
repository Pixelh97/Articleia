package com.example.viewmodel.postDetails

import androidx.lifecycle.SavedStateHandle
import com.example.domain.repository.PostRepository
import com.example.entity.Comment
import com.example.entity.Post
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class PostDetailsViewModelTest {
    private lateinit var repository: PostRepository
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: PostDetailsViewModel

    private val testPostId = 123
    private val testPost =
        Post(
            id = testPostId,
            title = "Test Post",
            body = "Test Body",
            userId = 1,
            isFavorite = false,
            commentsCounter = 2,
        )
    private val testComments =
        listOf(
            Comment(
                id = 1,
                postId = testPostId,
                name = "Comment 1",
                email = "test@test.com",
                body = "Body 1",
            ),
            Comment(
                id = 2,
                postId = testPostId,
                name = "Comment 2",
                email = "test2@test.com",
                body = "Body 2",
            ),
        )

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        savedStateHandle = mockk(relaxed = true)
        every { savedStateHandle.get<Int>("postId") } returns testPostId

        coEvery { repository.fetchCommentsByPostId(any()) } returns testComments
        coEvery { repository.fetchPostById(testPostId) } returns testPost
    }

    @Test
    fun `when viewModel is initialized, then fetchCommentsByPostId and fetchPostById are called`() =
        runTest {
            viewModel = PostDetailsViewModel(repository, savedStateHandle)

            coVerify { repository.fetchCommentsByPostId(1) }
            coVerify { repository.fetchPostById(testPostId) }
        }

    @Test
    fun `when viewModel is initialized with valid postId, then state is updated with post and comments`() =
        runTest {
            coEvery { repository.fetchCommentsByPostId(1) } returns testComments
            coEvery { repository.fetchPostById(testPostId) } returns testPost

            viewModel = PostDetailsViewModel(repository, savedStateHandle)

            coVerify { repository.fetchCommentsByPostId(1) }
            coVerify { repository.fetchPostById(testPostId) }
            assertEquals(false, viewModel.state.value.isLoading)
            assertEquals(testPostId, viewModel.state.value.postUiState.id)
            assertEquals(testComments.size, viewModel.state.value.comments.size)
            assertEquals(null, viewModel.state.value.error)
        }

    @Test
    fun `when fetchPostById returns null, then state contains empty PostUiState`() =
        runTest {
            coEvery { repository.fetchPostById(testPostId) } returns null

            viewModel = PostDetailsViewModel(repository, savedStateHandle)

            assertEquals(0, viewModel.state.value.postUiState.id)
            assertEquals(false, viewModel.state.value.isLoading)
        }

    @Test
    fun `when fetchCommentsByPostId throws exception, then error state is updated`() =
        runTest {
            val errorMessage = "Network error"
            coEvery { repository.fetchCommentsByPostId(any()) } throws Exception(errorMessage)

            viewModel = PostDetailsViewModel(repository, savedStateHandle)

            coVerify { repository.fetchCommentsByPostId(1) }
            assertEquals(false, viewModel.state.value.isLoading)
            assertEquals(errorMessage, viewModel.state.value.error)
        }

    @Test
    fun `when toggleFavorite is called with false, then togglePostFavorite is called and state is updated`() =
        runTest {
            viewModel = PostDetailsViewModel(repository, savedStateHandle)

            viewModel.toggleFavorite(false)

            coVerify { repository.togglePostFavorite(testPostId, false) }
            assertEquals(false, viewModel.state.value.postUiState.isFavorite)
        }

    @Test
    fun `when retryFetchingData is called, then fetchCommentsByPostId and fetchPostById are called`() =
        runTest {
            viewModel = PostDetailsViewModel(repository, savedStateHandle)

            viewModel.retryFetchingData()

            coVerify(exactly = 2) { repository.fetchCommentsByPostId(1) }
            coVerify(exactly = 2) { repository.fetchPostById(testPostId) }
        }

    @Test
    fun `when savedStateHandle returns null postId, then postId defaults to 0`() =
        runTest {
            every { savedStateHandle.get<Int>("postId") } returns null

            viewModel = PostDetailsViewModel(repository, savedStateHandle)

            assertEquals(0, viewModel.postId)
        }

    @Test
    fun `when savedStateHandle returns valid postId, then postId is set correctly`() =
        runTest {
            viewModel = PostDetailsViewModel(repository, savedStateHandle)

            assertEquals(testPostId, viewModel.postId)
        }
}
