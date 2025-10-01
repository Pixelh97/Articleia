package com.example.repository.repository

import android.content.Context
import com.example.domain.exceptions.ArticleiaException
import com.example.entity.Comment
import com.example.entity.Post
import com.example.repository.dataSource.local.PostLocalDataSource
import com.example.repository.dataSource.remote.PostRemoteDataSource
import com.example.repository.dto.local.LocalFavoriteQueueDto
import com.example.repository.dto.local.LocalPostDto
import com.example.repository.dto.remote.CommentDto
import com.example.repository.dto.remote.PostDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class PostRepositoryImplTest {
    private lateinit var localDataSource: PostLocalDataSource
    private lateinit var remoteDataSource: PostRemoteDataSource
    private lateinit var context: Context

    private lateinit var repository: PostRepositoryImpl

    private val testLocalPostDto =
        LocalPostDto(
            id = 1,
            title = "Test Post",
            body = "Test Body",
            userId = 1,
            isFavorite = false,
            commentsCounter = 5,
        )

    private val testPostDto =
        PostDto(
            id = 1,
            title = "Test Post",
            body = "Test Body",
            userId = 1,
        )

    private val testPost =
        Post(
            id = 1,
            title = "Test Post",
            body = "Test Body",
            userId = 1,
            isFavorite = false,
            commentsCounter = 5,
        )

    private val testCommentDto =
        CommentDto(
            id = 1,
            postId = 1,
            name = "Test Comment",
            email = "test@test.com",
            body = "Comment Body",
        )

    private val testComment =
        Comment(
            id = 1,
            postId = 1,
            name = "Test Comment",
            email = "test@test.com",
            body = "Comment Body",
        )

    @Before
    fun setUp() {
        localDataSource = mockk(relaxed = true)
        remoteDataSource = mockk(relaxed = true)
        context = mockk(relaxed = true)
        repository = PostRepositoryImpl(localDataSource, remoteDataSource, context)
    }

    @Test
    fun `when fetchPosts is called and cache is empty, then fetch from remote and cache locally`() =
        runTest {
            coEvery { localDataSource.fitchPosts() } returns emptyList()
            coEvery { remoteDataSource.fetchPosts() } returns listOf(testPostDto)
            coEvery { remoteDataSource.fetchCommentsByPostId(1) } returns listOf(testCommentDto)
            coEvery { localDataSource.getAllPendingFavorites() } returns emptyList()

            val result = repository.fetchPosts()

            coVerify { localDataSource.fitchPosts() }
            coVerify { remoteDataSource.fetchPosts() }
            coVerify { remoteDataSource.fetchCommentsByPostId(1) }
            coVerify { localDataSource.addAllPosts(any()) }
            assertEquals(1, result.size)
        }

    @Test
    fun `when fetchPosts is called and cache is not empty, then return cached posts`() =
        runTest {
            coEvery { localDataSource.fitchPosts() } returns listOf(testLocalPostDto)

            val result = repository.fetchPosts()

            coVerify { localDataSource.fitchPosts() }
            coVerify(exactly = 0) { remoteDataSource.fetchPosts() }
            assertEquals(1, result.size)
        }

    @Test
    fun `when fetchPostById is called and post exists in cache, then return cached post`() =
        runTest {
            coEvery { localDataSource.getPostById(1) } returns testLocalPostDto

            val result = repository.fetchPostById(1)

            coVerify { localDataSource.getPostById(1) }
            assertEquals(testPost.id, result?.id)
        }

    @Test
    fun `when fetchPostById is called and post does not exist in cache, then return null`() =
        runTest {
            coEvery { localDataSource.getPostById(1) } returns null

            val result = repository.fetchPostById(1)

            coVerify { localDataSource.getPostById(1) }
            assertNull(result)
        }

    @Test
    fun `when fetchCommentsByPostId is called, then fetch from remote and map to Comment`() =
        runTest {
            coEvery { remoteDataSource.fetchCommentsByPostId(1) } returns listOf(testCommentDto)

            val result = repository.fetchCommentsByPostId(1)

            coVerify { remoteDataSource.fetchCommentsByPostId(1) }
            assertEquals(1, result.size)
            assertEquals(testComment.id, result[0].id)
        }

    @Test
    fun `when togglePostFavorite is called with true and succeeds, then update cache and call remote`() =
        runTest {
            coEvery { localDataSource.getPostById(1) } returns testLocalPostDto
            coEvery { remoteDataSource.addPostToFavorites(1) } returns Unit

            repository.togglePostFavorite(1, true)

            coVerify { localDataSource.getPostById(1) }
            coVerify { localDataSource.updatePost(any()) }
            coVerify { remoteDataSource.addPostToFavorites(1) }
        }

    @Test
    fun `when togglePostFavorite is called with false and succeeds, then update cache and call remote`() =
        runTest {
            coEvery { localDataSource.getPostById(1) } returns testLocalPostDto
            coEvery { remoteDataSource.removePostFromFavorites(1) } returns Unit

            repository.togglePostFavorite(1, false)

            coVerify { localDataSource.getPostById(1) }
            coVerify { localDataSource.updatePost(any()) }
            coVerify { remoteDataSource.removePostFromFavorites(1) }
        }

    @Test
    fun `when togglePostFavorite fails and post is already in queue, then remove from queue`() =
        runTest {
            val pendingFavorite = LocalFavoriteQueueDto(postId = 1, isFavorite = false)
            coEvery { localDataSource.getPostById(1) } returns testLocalPostDto
            coEvery { remoteDataSource.addPostToFavorites(1) } throws ArticleiaException()
            coEvery { localDataSource.getAllPendingFavorites() } returns listOf(pendingFavorite)

            repository.togglePostFavorite(1, true)

            coVerify { localDataSource.removeFavoriteQueue(1, false) }
            coVerify(exactly = 0) { localDataSource.addFavoriteQueue(any(), any()) }
        }

    @Test
    fun `when fetchPosts with pending favorites, then posts reflect pending state`() =
        runTest {
            val pendingFavorite = LocalFavoriteQueueDto(postId = 1, isFavorite = true)
            coEvery { localDataSource.fitchPosts() } returns emptyList()
            coEvery { remoteDataSource.fetchPosts() } returns listOf(testPostDto)
            coEvery { remoteDataSource.fetchCommentsByPostId(1) } returns listOf(testCommentDto)
            coEvery { localDataSource.getAllPendingFavorites() } returns listOf(pendingFavorite)

            val result = repository.fetchPosts()

            coVerify { localDataSource.getAllPendingFavorites() }
            assertEquals(1, result.size)
        }

    @Test
    fun `when togglePostFavorite with null cached post, then updatePost is not called`() =
        runTest {
            coEvery { localDataSource.getPostById(1) } returns null
            coEvery { remoteDataSource.addPostToFavorites(1) } returns Unit

            repository.togglePostFavorite(1, true)

            coVerify { localDataSource.getPostById(1) }
            coVerify(exactly = 0) { localDataSource.updatePost(any()) }
            coVerify { remoteDataSource.addPostToFavorites(1) }
        }

    @Test
    fun `when fetchCommentsByPostId with multiple comments, then return all mapped comments`() =
        runTest {
            val comments =
                listOf(
                    testCommentDto,
                    testCommentDto.copy(id = 2),
                    testCommentDto.copy(id = 3),
                )
            coEvery { remoteDataSource.fetchCommentsByPostId(1) } returns comments

            val result = repository.fetchCommentsByPostId(1)

            assertEquals(3, result.size)
        }

    @Test
    fun `when fetchPosts with multiple posts, then fetch comments for each post`() =
        runTest {
            val posts =
                listOf(
                    testPostDto,
                    testPostDto.copy(id = 2),
                    testPostDto.copy(id = 3),
                )
            coEvery { localDataSource.fitchPosts() } returns emptyList()
            coEvery { remoteDataSource.fetchPosts() } returns posts
            coEvery { remoteDataSource.fetchCommentsByPostId(any()) } returns listOf(testCommentDto)
            coEvery { localDataSource.getAllPendingFavorites() } returns emptyList()

            val result = repository.fetchPosts()

            coVerify(exactly = 3) { remoteDataSource.fetchCommentsByPostId(any()) }
            assertEquals(3, result.size)
        }
}
