package com.thuypham.ptithcm.domain.usecase.movie

import com.thuypham.ptithcm.domain.repository.MovieRepository
import com.thuypham.ptithcm.baseapp.domain.usecase.BaseUseCaseNoParam
import com.thuypham.ptithcm.baselib.base.model.ResponseHandler
import com.thuypham.ptithcm.data.remote.response.MovieGenres

class GetMovieGenresUseCase(private val movieRepository: MovieRepository) :
    BaseUseCaseNoParam<ResponseHandler<MovieGenres>>() {

    override suspend fun invoke(): ResponseHandler<MovieGenres> {
        return movieRepository.getMovieGenres()
    }

}