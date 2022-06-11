package com.thuypham.ptithcm.baseapp.domain.usecase.movie

import com.thuypham.ptithcm.baseapp.domain.repository.MovieRepository
import com.thuypham.ptithcm.baseapp.domain.usecase.BaseUseCase
import com.thuypham.ptithcm.baselib.base.model.ResponseHandler
import com.thuypham.ptithcm.baseapp.data.remote.response.MovieResponse

class GetMovieTopRateUseCase(private val movieRepository: MovieRepository) :
    BaseUseCase<Int, ResponseHandler<MovieResponse>>() {

    override suspend fun invoke(param: Int): ResponseHandler<MovieResponse> {
        return movieRepository.getMovieTopRate(param)
    }
}