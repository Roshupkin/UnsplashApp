package android.com.roshchupkin.unsplashapp.network.entity


data class ImageNetworkEntity(
    val description: String?,
    val height: Int?,
    val id: String,
    val urls: UrlsNetworkEntity?,
    val user: UserNetworkEntity?,
    val width: Int?,
)