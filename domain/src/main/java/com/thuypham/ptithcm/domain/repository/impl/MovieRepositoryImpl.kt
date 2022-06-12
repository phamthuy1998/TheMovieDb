package com.thuypham.ptithcm.domain.repository.impl

import com.thuypham.ptithcm.baselib.base.model.ResponseHandler
import com.thuypham.ptithcm.data.remote.api.MovieV3Api
import com.thuypham.ptithcm.data.remote.api.wrapApiCall
import com.thuypham.ptithcm.data.remote.response.MovieGenres
import com.thuypham.ptithcm.data.remote.response.MovieResponse
import com.thuypham.ptithcm.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepositoryImpl(private val movieAPI: MovieV3Api) : MovieRepository {
    override suspend fun getMovieGenres(): ResponseHandler<MovieGenres> {
        return withContext(Dispatchers.IO) {
            wrapApiCall { movieAPI.getMovieGenres() }
        }
    }

    override suspend fun getMovieTrending(page: Int): ResponseHandler<MovieResponse> {
        return withContext(Dispatchers.IO) {
            wrapApiCall { movieAPI.getMovieTrending(page) }
        }

    }

    override suspend fun getMovieNowPlaying(page: Int): ResponseHandler<MovieResponse> {
        return withContext(Dispatchers.IO) {
            wrapApiCall { movieAPI.getNowPlaying(page) }
        }
    }

    override suspend fun getMovieUpComing(page: Int): ResponseHandler<MovieResponse> {
        return withContext(Dispatchers.IO) {
            wrapApiCall { movieAPI.getMovieUpComing(page) }
        }
    }

    override suspend fun getMoviePopular(page: Int): ResponseHandler<MovieResponse> {
        return withContext(Dispatchers.IO) {
            wrapApiCall { movieAPI.getMoviePopular(page) }
        }
    }

    override suspend fun getMovieTopRate(page: Int): ResponseHandler<MovieResponse> {
        return withContext(Dispatchers.IO) {
            wrapApiCall { movieAPI.getMovieTopRate(page) }
        }
    }
}