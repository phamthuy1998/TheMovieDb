package com.thuypham.ptithcm.data.remote.api

import com.thuypham.ptithcm.data.model.LoginParam
import com.thuypham.ptithcm.data.remote.response.LoginResponse
import com.thuypham.ptithcm.data.remote.response.MovieGenres
import com.thuypham.ptithcm.data.remote.response.MovieResponse
import com.thuypham.ptithcm.data.util.ApiHelper
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MovieV3Api {
    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("api_key") apiKey: String = ApiHelper.movieApiKey(),
        @Query("language") language: String = ApiHelper.getCurrentLanguage(),
    ): Response<MovieGenres>

    @GET("/trending/movie/week")
    suspend fun getMovieTrending(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = ApiHelper.movieApiKey(),
        @Query("language") language: String = ApiHelper.getCurrentLanguage(),
    ): Response<MovieResponse>


    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = ApiHelper.movieApiKey(),
        @Query("language") language: String = ApiHelper.getCurrentLanguage(),
    ): Response<MovieResponse>


    @GET("/movie/upcoming")
    suspend fun getMovieUpComing(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = ApiHelper.movieApiKey(),
        @Query("language") language: String = ApiHelper.getCurrentLanguage(),
    ): Response<MovieResponse>


    @GET("/movie/popular")
    suspend fun getMoviePopular(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = ApiHelper.movieApiKey(),
        @Query("language") language: String = ApiHelper.getCurrentLanguage(),
    ): Response<MovieResponse>


    @GET("/movie/top_rated")
    suspend fun getMovieTopRate(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = ApiHelper.movieApiKey(),
        @Query("language") language: String = ApiHelper.getCurrentLanguage(),
    ): Response<MovieResponse>


    @GET("authentication/token/new")
    suspend fun getNewToken(
        @Query("api_key") apiKey: String = ApiHelper.movieApiKey(),
    ): Response<LoginResponse>

    @POST("authentication/token/validate_with_login")
    suspend fun login(
        @Body loginParam: LoginParam,
        @Query("api_key") apiKey: String = ApiHelper.movieApiKey(),
    ): Response<LoginResponse>
}