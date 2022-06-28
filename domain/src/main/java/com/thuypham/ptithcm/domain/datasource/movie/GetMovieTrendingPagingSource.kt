package com.thuypham.ptithcm.domain.datasource.movie

import com.thuypham.ptithcm.baselib.base.extension.logD
import com.thuypham.ptithcm.data.remote.api.MovieV3Api
import com.thuypham.ptithcm.data.remote.response.ListResponse
import com.thuypham.ptithcm.data.remote.response.Movie
import com.thuypham.ptithcm.domain.datasource.BaseRemotePagingSource
import retrofit2.Response

class GetMovieTrendingPagingSource(private val movieApi: MovieV3Api) : BaseRemotePagingSource<Movie>() {
    init {
        logD("init -GetMovieTrendingPagingSource")

    }
    override suspend fun createApiCall(page: Int): Response<ListResponse<Movie>> {
        logD("thuyyyyyy -GetMovieTrendingPagingSource")
        return movieApi.getMovieTrending(page)
    }

}