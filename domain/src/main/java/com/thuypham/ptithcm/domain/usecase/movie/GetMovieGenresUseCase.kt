package com.thuypham.ptithcm.domain.usecase.movie

import com.thuypham.ptithcm.baseapp.domain.usecase.BaseUseCaseNoParam
import com.thuypham.ptithcm.baselib.base.model.ResponseHandler
import com.thuypham.ptithcm.data.remote.response.ListResponse
import com.thuypham.ptithcm.data.remote.response.MovieGenre
import com.thuypham.ptithcm.data.remote.response.MovieGenres
import com.thuypham.ptithcm.domain.repository.MovieRepository

class GetMovieGenresUseCase(private val movieRepository: MovieRepository) :
    BaseUseCaseNoParam<ResponseHandler<MovieGenres>>() {

    override suspend fun invoke(): ResponseHandler<MovieGenres> {
        return movieRepository.getMovieGenres()
    }

}