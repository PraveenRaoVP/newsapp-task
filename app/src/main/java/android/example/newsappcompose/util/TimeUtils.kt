package android.example.newsappcompose.util

import android.os.Build
import androidx.annotation.RequiresApi

class TimeUtils {
    companion object {
        // calculate number of hours or days before the current time -
        // format of the input : Example 2024-05-28T19:05:45Z

        @RequiresApi(Build.VERSION_CODES.O)
        fun getTimeAgo(time: String): String {
            val currentTime = System.currentTimeMillis()
            val timeAgo = time.substring(0, 10) + "T" + time.substring(11, 19) + "Z"
            val timeInMillis = java.time.Instant.parse(timeAgo).toEpochMilli()
            val diff = currentTime - timeInMillis
            val seconds = diff / 1000
            val minutes = seconds / 60
            val hours = minutes / 60
            val days = hours / 24
            return when {
                days > 0 -> "${days} ${if(days==1L) "day" else "days"} ago"
                hours > 0 -> "$hours hours ago"
                minutes > 0 -> "$minutes minutes ago"
                else -> "$seconds seconds ago"
            }
        }
    }

}