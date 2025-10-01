package com.example.localdatasource.roomDataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.localdatasource.roomDataBase.dao.PostFavoriteQueueDao
import com.example.localdatasource.roomDataBase.dao.PostsDao
import com.example.repository.dto.local.LocalFavoriteQueueDto
import com.example.repository.dto.local.LocalPostDto
import kotlin.jvm.java

@Database(
    entities = [LocalPostDto::class, LocalFavoriteQueueDto::class],
    version = 2,
    exportSchema = true,
)
abstract class ArticleiaDataBase : RoomDatabase() {
    abstract fun postDao(): PostsDao

    abstract fun postFavoriteQueueDao(): PostFavoriteQueueDao

    companion object {
        private const val DATABASE_NAME = "ArticleiaDatabase"

        @Volatile
        private var instance: ArticleiaDataBase? = null

        fun getInstance(context: Context): ArticleiaDataBase =
            instance ?: synchronized(this) {
                buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(context: Context): ArticleiaDataBase =
            Room
                .databaseBuilder(context, ArticleiaDataBase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration(false)
                .build()
    }
}
