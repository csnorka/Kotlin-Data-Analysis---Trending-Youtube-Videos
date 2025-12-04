package data

data class Categories(
    val kind: String,
    val etag: String,
    val items: List<VideoCategory>
)