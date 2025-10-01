package com.example.viewmodel.posts

interface PostsInteractionListener {
    fun onPostClick(postId: Int)
    fun onTabSelected(tabIndex: Int)
}