package dav.project.chirp.postItem

import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit

data class Item(val postDescription: String, val postImage: Int,  val date: Date){

    fun getFormattedDate(): String {
        val currentDate = Date()
        val diffInMillis = currentDate.time - date.time
        val minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis)

        return when {
            minutes < 1 -> "Just now"
            minutes < 1440 -> SimpleDateFormat("hh:mm a").format(date)
            else -> SimpleDateFormat("dd/MM/yyyy").format(date)
        }
    }
}
