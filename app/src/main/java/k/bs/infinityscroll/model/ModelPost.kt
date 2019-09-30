package k.bs.infinityscroll.model

data class ModelPost(
    var body: String = "",
    val id: Int = 0,
    var title: String = "",
    val userId: Int = 0,
    var postDeleted: Boolean = false
)