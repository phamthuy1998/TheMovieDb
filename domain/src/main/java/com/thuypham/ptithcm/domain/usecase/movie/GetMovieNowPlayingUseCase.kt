package com.thuypham.ptithcm.domain.usecase.movie

import com.thuypham.ptithcm.baseapp.domain.usecase.BaseUseCase
import com.thuypham.ptithcm.baselib.base.model.ResponseHandler
import com.thuypham.ptithcm.data.remote.response.ListResponse
import com.thuypham.ptithcm.data.remote.response.Movie
import com.thuypham.ptithcm.domain.repository.MovieRepository

class GetMovieNowPlayingUseCase(private val movieRepository: MovieRepository) :
    BaseUseCase<Int, ResponseHandler<ListResponse<Movie>>>() {

    override suspend fun invoke(param: Int): ResponseHandler<ListResponse<Movie>> {
        return movieRepository.getMovieNowPlaying(param)
    }
}