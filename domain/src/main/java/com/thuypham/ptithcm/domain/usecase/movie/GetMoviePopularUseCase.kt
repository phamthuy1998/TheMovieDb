package com.thuypham.ptithcm.domain.usecase.movie

import com.thuypham.ptithcm.baselib.base.model.ResponseHandler
import com.thuypham.ptithcm.data.remote.response.MovieResponse
import com.thuypham.ptithcm.data.repository.MovieRepository
import com.thuypham.ptithcm.domain.usecase.BaseUseCase

class GetMoviePopularUseCase(private val movieRepository: MovieRepository) :
    BaseUseCase<Int, ResponseHandler<MovieResponse>>() {

    override suspend fun invoke(param: Int): ResponseHandler<MovieResponse> {
        return movieRepository.getMoviePopular(param)
    }
}