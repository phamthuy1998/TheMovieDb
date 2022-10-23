package com.thuypham.ptithcm.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.thuypham.ptithcm.baselib.base.model.ResponseHandler
import com.thuypham.ptithcm.data.remote.response.*

interface MovieRepository {
    suspend fun getMovieGenres(): ResponseHandler<MovieGenres>
    suspend fun getMovieTrending(page: Int = 1): ResponseHandler<ListResponse<Movie>>
    suspend fun getMovieNowPlaying(page: Int = 1): ResponseHandler<ListResponse<Movie>>
    suspend fun getMovieUpComing(page: Int = 1): ResponseHandler<ListResponse<Movie>>
    suspend fun getMoviePopular(page: Int = 1): ResponseHandler<ListResponse<Movie>>
    suspend fun getMovieTopRate(page: Int = 1): ResponseHandler<ListResponse<Movie>>

    suspend fun getMovieTrendingPaging(): LiveData<PagingData<Movie>>
    suspend fun getMovieNowPlayingPaging(): LiveData<PagingData<Movie>>
    suspend fun getMovieUpComingPaging(): LiveData<PagingData<Movie>>
    suspend fun getMoviePopularPaging(): LiveData<PagingData<Movie>>
    suspend fun getMovieTopRatePaging(): LiveData<PagingData<Movie>>

    suspend fun getMovieByGenreId(genreId: Int): LiveData<PagingData<Movie>>
    suspend fun getMovieDetail(movieID: Int): ResponseHandler<MovieDetail>

    suspend fun getMoviesRecommendation(movieID: Int): ResponseHandler<ListResponse<Movie>>
    suspend fun getMoviesRecommendationPaging(movieID: Int): LiveData<PagingData<Movie>>
    suspend fun getSimilarMoviesPaging(movieID: Int): LiveData<PagingData<Movie>>
    suspend fun getSimilarMovies(movieID: Int): ResponseHandler<ListResponse<Movie>>

    suspend fun getMovieImages(movieID: Int): ResponseHandler<MovieImage>

    suspend fun getMovieVideo(movieID: Int): ResponseHandler<MovieVideo>
    suspend fun getMovieCast(movieID: Int): ResponseHandler<MovieCast>
}