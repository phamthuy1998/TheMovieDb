package com.thuypham.ptithcm.domain.datasource.movie

import com.thuypham.ptithcm.data.remote.api.MovieV3Api
import com.thuypham.ptithcm.data.remote.response.ListResponse
import com.thuypham.ptithcm.data.remote.response.Movie
import com.thuypham.ptithcm.domain.datasource.BaseRemotePagingSource
import retrofit2.Response

class GetMoviePopularPagingSource(private val movieApi: MovieV3Api) : BaseRemotePagingSource<Movie>() {
    override suspend fun createApiCall(page: Int): Response<ListResponse<Movie>> {
        return movieApi.getMoviePopular(page)
    }

}