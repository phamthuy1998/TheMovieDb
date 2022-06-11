package com.thuypham.ptithcm.baseapp.domain.repository.impl

import com.thuypham.ptithcm.baseapp.data.remote.api.MovieV3Api
import com.thuypham.ptithcm.baselib.base.model.ResponseHandler
import com.thuypham.ptithcm.baseapp.data.remote.response.MovieGenres
import com.thuypham.ptithcm.baseapp.data.remote.response.MovieResponse
import com.thuypham.ptithcm.baseapp.domain.repository.MovieRepository

class MovieRepositoryImpl(private val movieAPI: MovieV3Api) : MovieRepository {
    override suspend fun getMovieGenres(): ResponseHandler<MovieGenres> {
        return com.thuypham.ptithcm.baseapp.data.remote.response.api.wrapApiCall {
            movieAPI.getMovieGenres()
        }
    }

    override suspend fun getMovieTrending(page: Int): ResponseHandler<MovieResponse> {
        return com.thuypham.ptithcm.baseapp.data.remote.response.api.wrapApiCall {
            movieAPI.getMovieTrending(page)
        }

    }

    override suspend fun getMovieNowPlaying(page: Int): ResponseHandler<MovieResponse> {
        return com.thuypham.ptithcm.baseapp.data.remote.response.api.wrapApiCall {
            movieAPI.getNowPlaying(page)
        }
    }

    override suspend fun getMovieUpComing(page: Int): ResponseHandler<MovieResponse> {
        return com.thuypham.ptithcm.baseapp.data.remote.response.api.wrapApiCall {
            movieAPI.getMovieUpComing(page)
        }
    }

    override suspend fun getMoviePopular(page: Int): ResponseHandler<MovieResponse> {
        return com.thuypham.ptithcm.baseapp.data.remote.response.api.wrapApiCall {
            movieAPI.getMoviePopular(page)
        }
    }

    override suspend fun getMovieTopRate(page: Int): ResponseHandler<MovieResponse> {
        return com.thuypham.ptithcm.baseapp.data.remote.response.api.wrapApiCall {
            movieAPI.getMovieTopRate(page)
        }
    }
}