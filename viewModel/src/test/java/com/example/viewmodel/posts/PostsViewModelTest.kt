package com.example.viewmodel.posts

import com.example.domain.exceptions.ArticleiaException
import com.example.domain.repository.PostRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class PostsViewModelTest {
    lateinit var repository: PostRepository
    lateinit var viewModel: PostsViewModel

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        viewModel = PostsViewModel(repository)
    }

    @Test
    fun `when viewModel is initialized, then fetchPosts is called`() =
        runTest {
            viewModel = PostsViewModel(repository)
            coVerify { repository.fetchPosts() }
        }

    @Test
    fun `when onTabSelected is called, then fetchPosts is called`() =
        runTest {
            viewModel.onTabSelected(0)
            coVerify { repository.fetchPosts() }
        }

    @Test
    fun `when onTabSelected with favorites is called, then fetchPosts is called`() =
        runTest {
            viewModel.onTabSelected(1)
            coVerify { repository.fetchPosts() }
        }

    @Test
    fun `when onTabSelected with invalid index is called, then fetchPosts is called`() =
        runTest {
            viewModel.onTabSelected(999)
            coVerify { repository.fetchPosts() }
        }

    @Test
    fun `when fetchData throws an exception the isNoInternetConnection state should be true`() =
        runTest {
            coEvery { repository.fetchPosts() } throws ArticleiaException()

            viewModel = PostsViewModel(repository)

            coVerify { repository.fetchPosts() }
            assertEquals(true, viewModel.state.value.isNoInternetConnection)
            assertEquals(false, viewModel.state.value.isLoading)
        }

    @Test
    fun `when call retryFetchingPosts, then fetchPosts is called`() =
        runTest {
            viewModel.retryFetchingPosts()
            coVerify { repository.fetchPosts() }
        }
}
