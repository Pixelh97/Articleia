package com.example.remotedatasource.dataSource

import com.example.remotedatasource.client.NetworkClient
import com.example.repository.dto.remote.CommentDto
import com.example.repository.dto.remote.PostDto
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PostRemoteDataSourceImplTest {
    private lateinit var networkClient: NetworkClient
    private lateinit var postRemoteDataSourceImpl: PostRemoteDataSourceImpl

    private val jsonSerializer = Json { ignoreUnknownKeys = true }

    @Before
    fun setUp() {
        networkClient = mockk(relaxed = true)
        postRemoteDataSourceImpl = PostRemoteDataSourceImpl(networkClient)
    }

    @Test
    fun `fetchPosts should return a list of posts`() =
        runTest {
            // Given
            val jsonString =
                """
                [
                  {
                    "userId": 1,
                    "id": 1,
                    "title": "title 1",
                    "body": "body 1"
                  },
                  {
                    "userId": 1,
                    "id": 2,
                    "title": "title 2",
                    "body": "body 2"
                  }
                ]

                """.trimIndent()

            val expectedCategoryResponse =
                jsonSerializer.decodeFromString<List<PostDto>>(jsonString)

            val mockHttpResponse = mockk<HttpResponse>(relaxed = true)

            coEvery { mockHttpResponse.status } returns HttpStatusCode.OK
            coEvery { mockHttpResponse.body<List<PostDto>>() } returns expectedCategoryResponse

            coEvery {
                networkClient.get("/posts", any())
            } returns mockHttpResponse

            // When
            val posts = postRemoteDataSourceImpl.fetchPosts()

            // Then
            assertEquals(2, posts.size)
            assertEquals("title 1", posts[0].title)
            assertEquals(2, posts[1].id)
        }

    @Test
    fun `fetchCommentsByPostId should return a list of comments for the given post id`() =
        runTest {
            // Given
            val jsonString =
                """
                [
                  {
                    "postId": 1,
                    "id": 1,
                    "name": "name 1",
                    "email": "Eliseo@gardner.biz",
                    "body": "body 1"
                  },
                  {
                    "postId": 1,
                    "id": 2,
                    "name": "name 2",
                    "email": "Jayne_Kuhic@sydney.com",
                    "body": "body 2"
                  }
                ]
                """.trimIndent()
            val postId = 1

            val expectedCategoryResponse =
                jsonSerializer.decodeFromString<List<CommentDto>>(jsonString)

            val mockHttpResponse = mockk<HttpResponse>(relaxed = true)

            coEvery { mockHttpResponse.status } returns HttpStatusCode.OK
            coEvery { mockHttpResponse.body<List<CommentDto>>() } returns expectedCategoryResponse

            coEvery {
                networkClient.get("/posts/$postId/comments", any())
            } returns mockHttpResponse

            // When
            val comments = postRemoteDataSourceImpl.fetchCommentsByPostId(postId)

            // Then
            assertEquals(2, comments.size)
            assertEquals("Eliseo@gardner.biz", comments[0].email)
            assertEquals(2, comments[1].id)
        }
}
