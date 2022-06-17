package com.thuypham.ptithcm.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.thuypham.ptithcm.baselib.base.model.ResponseHandler
import com.thuypham.ptithcm.data.remote.response.ListResponse
import com.thuypham.ptithcm.data.remote.response.Movie
import com.thuypham.ptithcm.data.remote.response.MovieGenre
import com.thuypham.ptithcm.data.remote.response.MovieGenres

interface MovieRepository {
    suspend fun getMovieGenres(): ResponseHandler<MovieGenres>
    suspend fun getMovieTrending(page: Int=1): ResponseHandler<ListResponse<Movie>>
    suspend fun getMovieNowPlaying(page: Int=1): ResponseHandler<ListResponse<Movie>>
    suspend fun getMovieUpComing(page: Int=1): ResponseHandler<ListResponse<Movie>>
    suspend fun getMoviePopular(page: Int=1): ResponseHandler<ListResponse<Movie>>
    suspend fun getMovieTopRate(page: Int=1): ResponseHandler<ListResponse<Movie>>

    suspend fun getMovieTrendingPaging(): LiveData<PagingData<Movie>>
    suspend fun getMovieNowPlayingPaging(): LiveData<PagingData<Movie>>
    suspend fun getMovieUpComingPaging(): LiveData<PagingData<Movie>>
    suspend fun getMoviePopularPaging(): LiveData<PagingData<Movie>>
    suspend fun getMovieTopRatePaging(): LiveData<PagingData<Movie>>
}