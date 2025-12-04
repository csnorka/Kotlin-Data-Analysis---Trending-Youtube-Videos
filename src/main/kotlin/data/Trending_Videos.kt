package data

import org.apache.commons.csv.CSVRecord
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class Trending_Videos(data: CSVRecord) {
    val videoId: String = data[0]

    val trendingDate: LocalDate = data[1].toLocalDateCustom()

    val title: String = data[2]
    val channelTitle: String = data[3]
    val categoryId: String = data[4]

    val publishTime: LocalDateTime = try {
        LocalDateTime.parse(data[5], DateTimeFormatter.ISO_DATE_TIME)
    } catch (e: Exception) {
        LocalDateTime.now()
    }

    val tags: String = data[6]
    val views: Long = data[7].toLongOrNull() ?: 0L
    val likes: Long = data[8].toLongOrNull() ?: 0L
    val dislikes: Long = data[9].toLongOrNull() ?: 0L
    val commentCount: Long = data[10].toLongOrNull() ?: 0L

    val thumbnailLink: String = data[11]
    val commentsDisabled: Boolean = data[12].toBoolean()
    val ratingsDisabled: Boolean = data[13].toBoolean()
    val videoErrorOrRemoved: Boolean = data[14].toBoolean()
    val description: String = if (data.size() > 15) data[15] else ""
}

private val DATE_FORMATTER = DateTimeFormatter.ofPattern("yy.dd.MM")

private fun String.toLocalDateCustom(): LocalDate {
    return try {
        LocalDate.parse(this, DATE_FORMATTER)
    } catch (e: DateTimeParseException) {
        // Ha a formátum nem egyezik, a mai dátumot adjuk vissza, hogy ne álljon le a program
        LocalDate.now()
    }
}