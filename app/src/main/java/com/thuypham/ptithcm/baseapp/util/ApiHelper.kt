package com.thuypham.ptithcm.baseapp.util

class ApiHelper {
    companion object {
        init {
            System.loadLibrary("native-lib")
        }

        @JvmStatic
        external fun baseMovieV3Url(): String

        @JvmStatic
        external fun baseImageV3Url(): String

        @JvmStatic
        external fun movieApiKey(): String

        private var currentLanguage: String? = null

        private const val DEFAULT_LANGUAGE = "en-US"

        fun getCurrentLanguage(): String {
            return if (currentLanguage.isNullOrBlank()) DEFAULT_LANGUAGE else currentLanguage!!
        }
    }
}