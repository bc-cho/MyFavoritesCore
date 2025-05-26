package com.tryanything.myfavorites.utils

object ImageHelper {
    /**
     * Google Place APIの指定されたPhotoNameをリクエスト可能なURLに変換する
     */
    fun changeToGooglePhotoUrl(
        name: String,
        apiKey: String,
        maxWidth: Int = PHOTO_MAX_WIDTH_PX,
        maxHeight: Int = PHOTO_MAX_HEIGHT_PX
    ): String =
        "https://places.googleapis.com/v1/$name/media?key=$apiKey&maxHeightPx=$maxHeight&maxWidthPx=$maxWidth"

    private const val PHOTO_MAX_WIDTH_PX: Int = 200
    private const val PHOTO_MAX_HEIGHT_PX: Int = 200
}
