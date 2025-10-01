package com.example.remotedatasource.client

import android.R.attr.level
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class KtorClient(
    private val json: Json,
) : NetworkClient {
    private val httpClient =
        HttpClient {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 30_000
                connectTimeoutMillis = 10_000
                socketTimeoutMillis = 30_000
            }

            install(ContentNegotiation) { json(json) }

            defaultRequest {
                url(BASE_URL)
            }
        }

    override suspend fun get(
        url: String,
        block: HttpRequestBuilder.() -> Unit,
    ): HttpResponse = httpClient.get(url, block)

    companion object {
        private const val BASE_URL = "https://jsonplaceholder.typicode.com"
    }
}
