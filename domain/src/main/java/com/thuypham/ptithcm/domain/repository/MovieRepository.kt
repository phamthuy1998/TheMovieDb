package com.thuypham.ptithcm.domain.repository

import com.thuypham.ptithcm.baselib.base.model.ResponseHandler
import com.thuypham.ptithcm.data.remote.response.MovieGenres
import com.thuypham.ptithcm.data.remote.response.MovieResponse

interface MovieRepository {
    suspend fun getMovieGenres(): ResponseHandler<MovieGenres>
    suspend fun getMovieTrending(page: Int): ResponseHandler<MovieResponse>
    suspend fun getMovieNowPlaying(page: Int): ResponseHandler<MovieResponse>
    suspend fun getMovieUpComing(page: Int): ResponseHandler<MovieResponse>
    suspend fun getMoviePopular(page: Int): ResponseHandler<MovieResponse>
    suspend fun getMovieTopRate(page: Int): ResponseHandler<MovieResponse>
}