package com.thuypham.ptithcm.data.util

class ApiHelper {
    companion object {
        const val CONNECTION_TIME_OUT_SECOND = 50L
        const val MAIN_API = "MAIN_API"

        init {
            System.loadLibrary("native-lib")
        }

        @JvmStatic
        external fun baseMovieV3Url(): String

        @JvmStatic
        external fun baseImageV3Url(): String

        fun getImagePath(path: String) = baseImageV3Url().format(path)

        @JvmStatic
        external fun movieApiKey(): String

        private var currentLanguage: String? = null

        private const val DEFAULT_LANGUAGE = "en-US"

        fun getCurrentLanguage(): String {
            return if (currentLanguage.isNullOrBlank()) DEFAULT_LANGUAGE else currentLanguage!!
        }
    }
}