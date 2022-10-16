package com.thuypham.ptithcm.data.remote.api

import com.thuypham.ptithcm.data.param.LoginParam
import com.thuypham.ptithcm.data.remote.response.*
import com.thuypham.ptithcm.data.util.ApiHelper
import retrofit2.Response
import retrofit2.http.*

interface MovieV3Api {
    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("language") language: String = ApiHelper.getCurrentLanguage(),
    ): Response<MovieGenres>

    @GET("trending/movie/week")
    suspend fun getMovieTrending(
        @Query("page") page: Int = 1,
        @Query("language") language: String = ApiHelper.getCurrentLanguage(),
    ): Response<ListResponse<Movie>>


    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("page") page: Int = 1,
        @Query("language") language: String = ApiHelper.getCurrentLanguage(),
    ): Response<ListResponse<Movie>>


    @GET("movie/upcoming")
    suspend fun getMovieUpComing(
        @Query("page") page: Int = 1,
        @Query("language") language: String = ApiHelper.getCurrentLanguage(),
    ): Response<ListResponse<Movie>>


    @GET("movie/popular")
    suspend fun getMoviePopular(
        @Query("page") page: Int = 1,
        @Query("language") language: String = ApiHelper.getCurrentLanguage(),
    ): Response<ListResponse<Movie>>


    @GET("movie/top_rated")
    suspend fun getMovieTopRate(
        @Query("page") page: Int = 1,
        @Query("language") language: String = ApiHelper.getCurrentLanguage(),
    ): Response<ListResponse<Movie>>


    @GET("discover/movie")
    suspend fun getMovieByGenreID(
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int = 1,
        @Query("language") language: String = ApiHelper.getCurrentLanguage(),
    ): Response<ListResponse<Movie>>

    @GET("person/popular")
    suspend fun getPopularPeople(
        @Query("page") page: Int = 1,
        @Query("language") language: String = ApiHelper.getCurrentLanguage(),
    ): Response<ListResponse<Person>>


    @GET("person/{person_id}")
    suspend fun getPersonDetail(
        @Path("person_id") personId: Int = 0,
        @Query("language") language: String = ApiHelper.getCurrentLanguage(),
    ): Response<PersonDetail>


    @GET("person/{person_id}/images")
    suspend fun getPersonImage(
        @Path("person_id") personId: Int = 0,
        @Query("language") language: String = ApiHelper.getCurrentLanguage(),
    ): Response<PersonImage>

    @GET("person/{person_id}/movie_credits")
    suspend fun getPersonMovie(
        @Path("person_id") personId: Int = 0,
        @Query("language") language: String = ApiHelper.getCurrentLanguage(),
    ): Response<PersonMovie>


    @GET("person/{person_id}/tv_credits")
    suspend fun getPersonTVShows(
        @Path("person_id") personId: Int = 0,
        @Query("language") language: String = ApiHelper.getCurrentLanguage(),
    ): Response<PersonMovie>

    @GET("authentication/token/new")
    suspend fun getNewToken(): Response<LoginResponse>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideo(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = ApiHelper.getCurrentLanguage(),
    ): Response<MovieVideo>


    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = ApiHelper.getCurrentLanguage(),
    ): Response<MovieDetail>


    @GET("movie/{movie_id}/images")
    suspend fun getMovieImages(
        @Path("movie_id") movieId: Int,
    ): Response<MovieImage>

    @GET("movie/{movie_id}/recommendations")
    suspend fun getMoviesRecommendation(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int = 1,
        @Query("language") language: String = ApiHelper.getCurrentLanguage(),
    ): Response<ListResponse<Movie>>


    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int = 1,
        @Query("language") language: String = ApiHelper.getCurrentLanguage(),
    ): Response<ListResponse<Movie>>

    @POST("authentication/token/validate_with_login")
    suspend fun login(
        @Body loginParam: LoginParam,
    ): Response<LoginResponse>
}