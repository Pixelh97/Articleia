package com.example.localdatasource.roomDataBase.dataSource

import com.example.localdatasource.roomDataBase.dao.PostFavoriteQueueDao
import com.example.localdatasource.roomDataBase.dao.PostsDao
import com.example.repository.dto.local.LocalFavoriteQueueDto
import com.example.repository.dto.local.LocalPostDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class PostLocalDataSourceImplTest {
    private lateinit var postDao: PostsDao
    private lateinit var postFavoriteQueueDao: PostFavoriteQueueDao
    private lateinit var localDataSource: PostLocalDataSourceImpl

    private val testPostDto =
        LocalPostDto(
            id = 1,
            title = "Test Post",
            body = "Test Body",
            userId = 1,
            isFavorite = false,
            commentsCounter = 5,
        )

    private val testFavoriteQueueDto =
        LocalFavoriteQueueDto(
            postId = 1,
            isFavorite = true,
        )

    @Before
    fun setUp() {
        postDao = mockk(relaxed = true)
        postFavoriteQueueDao = mockk(relaxed = true)
        localDataSource = PostLocalDataSourceImpl(postDao, postFavoriteQueueDao)
    }

    @Test
    fun `when addFavoriteQueue is called, then insert is called on dao`() =
        runTest {
            localDataSource.addFavoriteQueue(1, true)

            coVerify { postFavoriteQueueDao.insert(LocalFavoriteQueueDto(1, true)) }
        }

    @Test
    fun `when addFavoriteQueue is called with false, then insert is called with false`() =
        runTest {
            localDataSource.addFavoriteQueue(2, false)

            coVerify { postFavoriteQueueDao.insert(LocalFavoriteQueueDto(2, false)) }
        }

    @Test
    fun `when removeFavoriteQueue is called, then delete is called on dao`() =
        runTest {
            localDataSource.removeFavoriteQueue(1, true)

            coVerify { postFavoriteQueueDao.delete(LocalFavoriteQueueDto(1, true)) }
        }

    @Test
    fun `when removeFavoriteQueue is called with false, then delete is called with false`() =
        runTest {
            localDataSource.removeFavoriteQueue(2, false)

            coVerify { postFavoriteQueueDao.delete(LocalFavoriteQueueDto(2, false)) }
        }

    @Test
    fun `when getAllPendingFavorites is called, then getAll is called on dao`() =
        runTest {
            val expectedList = listOf(testFavoriteQueueDto)
            coEvery { postFavoriteQueueDao.getAll() } returns expectedList

            val result = localDataSource.getAllPendingFavorites()

            coVerify { postFavoriteQueueDao.getAll() }
            assertEquals(expectedList, result)
        }

    @Test
    fun `when getAllPendingFavorites returns empty list, then return empty list`() =
        runTest {
            coEvery { postFavoriteQueueDao.getAll() } returns emptyList()

            val result = localDataSource.getAllPendingFavorites()

            coVerify { postFavoriteQueueDao.getAll() }
            assertEquals(0, result.size)
        }

    @Test
    fun `when addAllPosts is called, then insertAll is called on dao`() =
        runTest {
            val posts = listOf(testPostDto, testPostDto.copy(id = 2))

            localDataSource.addAllPosts(posts)

            coVerify { postDao.insertAll(posts) }
        }

    @Test
    fun `when addAllPosts is called with empty list, then insertAll is called with empty list`() =
        runTest {
            localDataSource.addAllPosts(emptyList())

            coVerify { postDao.insertAll(emptyList()) }
        }

    @Test
    fun `when updatePost is called, then upsert is called on dao`() =
        runTest {
            localDataSource.updatePost(testPostDto)

            coVerify { postDao.upsert(testPostDto) }
        }

    @Test
    fun `when fitchPosts is called, then getAllPosts is called on dao`() =
        runTest {
            val expectedPosts = listOf(testPostDto)
            coEvery { postDao.getAllPosts() } returns expectedPosts

            val result = localDataSource.fitchPosts()

            coVerify { postDao.getAllPosts() }
            assertEquals(expectedPosts, result)
        }

    @Test
    fun `when fitchPosts returns empty list, then return empty list`() =
        runTest {
            coEvery { postDao.getAllPosts() } returns emptyList()

            val result = localDataSource.fitchPosts()

            coVerify { postDao.getAllPosts() }
            assertEquals(0, result.size)
        }

    @Test
    fun `when getPostById is called with valid id, then getPostById is called on dao and returns post`() =
        runTest {
            coEvery { postDao.getPostById(1) } returns testPostDto

            val result = localDataSource.getPostById(1)

            coVerify { postDao.getPostById(1) }
            assertEquals(testPostDto, result)
        }

    @Test
    fun `when getPostById is called with invalid id, then return null`() =
        runTest {
            coEvery { postDao.getPostById(999) } returns null

            val result = localDataSource.getPostById(999)

            coVerify { postDao.getPostById(999) }
            assertNull(result)
        }

    @Test
    fun `when addAllPosts is called with multiple posts, then insertAll is called with all posts`() =
        runTest {
            val posts =
                listOf(
                    testPostDto,
                    testPostDto.copy(id = 2),
                    testPostDto.copy(id = 3),
                    testPostDto.copy(id = 4),
                )

            localDataSource.addAllPosts(posts)

            coVerify { postDao.insertAll(posts) }
        }

    @Test
    fun `when getAllPendingFavorites returns multiple items, then return all items`() =
        runTest {
            val favoriteQueue =
                listOf(
                    testFavoriteQueueDto,
                    testFavoriteQueueDto.copy(postId = 2),
                    testFavoriteQueueDto.copy(postId = 3, isFavorite = false),
                )
            coEvery { postFavoriteQueueDao.getAll() } returns favoriteQueue

            val result = localDataSource.getAllPendingFavorites()

            assertEquals(3, result.size)
            assertEquals(1, result[0].postId)
            assertEquals(2, result[1].postId)
            assertEquals(3, result[2].postId)
        }

    @Test
    fun `when fitchPosts returns multiple posts, then return all posts`() =
        runTest {
            val posts =
                listOf(
                    testPostDto,
                    testPostDto.copy(id = 2, isFavorite = true),
                    testPostDto.copy(id = 3, commentsCounter = 10),
                )
            coEvery { postDao.getAllPosts() } returns posts

            val result = localDataSource.fitchPosts()

            assertEquals(3, result.size)
            assertEquals(false, result[0].isFavorite)
            assertEquals(true, result[1].isFavorite)
            assertEquals(10, result[2].commentsCounter)
        }
}
