package com.thuypham.ptithcm.domain.usecase.movie

import com.thuypham.ptithcm.domain.repository.MovieRepository
import com.thuypham.ptithcm.baseapp.domain.usecase.BaseUseCase
import com.thuypham.ptithcm.baselib.base.model.ResponseHandler
import com.thuypham.ptithcm.data.remote.response.MovieResponse

class GetMoviePopularUseCase(private val movieRepository: MovieRepository) :
    BaseUseCase<Int, ResponseHandler<MovieResponse>>() {

    override suspend fun invoke(param: Int): ResponseHandler<MovieResponse> {
        return movieRepository.getMoviePopular(param)
    }
}