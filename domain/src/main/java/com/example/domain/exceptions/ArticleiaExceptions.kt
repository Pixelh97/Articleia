package com.example.domain.exceptions

open class ArticleiaException : Exception()

open class NetworkException : ArticleiaException()

class NoInternetException : NetworkException()

class ServerErrorException : NetworkException()
