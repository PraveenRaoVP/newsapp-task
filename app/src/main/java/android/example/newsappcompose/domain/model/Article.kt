package android.example.newsappcompose.domain.model

import android.example.newsappcompose.domain.usecases.news.AddCustomArticle
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Article")
data class Article(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    @PrimaryKey val url: String,
    val urlToImage: String,
    val isCustomArticle: Boolean = false
) : Parcelable

/* what happens when we annotate the class with @Serializable and the fields with @SerializedName?
* The class will be serialized and the fields will be serialized with the given name.
* What is the use of serialization here?
* Serialization is the process of converting an object into a stream of bytes to store the object or transmit it to memory, a database, or a file.
* What is the use of @SerializedName?
* The @SerializedName annotation is used to specify the name of the field when it is serialized.
* What is the use of @Serializable?
* The @Serializable annotation is used to mark a class as serializable.
* Uses of serialization:-
* 1. To save the state of an object.
* 2. To send the state of an object to a remote location.
* 3. To send the state of an object to a database.
* 4. To send the state of an object to a file.
* 5. To send the state of an object to memory.
*/