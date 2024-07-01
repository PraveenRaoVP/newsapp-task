package android.example.newsappcompose.data.local

import android.example.newsappcompose.domain.model.Article
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article)

    @Delete
    suspend fun delete(article: Article)

    @Query("SELECT * FROM Article")
    fun getArticles() : Flow<List<Article>>

    @Query("SELECT * FROM Article WHERE url=:url AND isCustomArticle=0")
    suspend fun getArticle(url: String) : Article?

    @Query("SELECT * FROM Article WHERE isCustomArticle=1")
    fun getCustomArticles() : Flow<List<Article>>

    @Query("SELECT * FROM Article WHERE url=:url AND isCustomArticle=1")
    suspend fun getCustomArticle(url: String) : Article?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCustomArticle(article: Article)
}