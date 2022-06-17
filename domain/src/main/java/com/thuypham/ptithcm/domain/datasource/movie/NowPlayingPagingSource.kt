package com.thuypham.ptithcm.domain.datasource.movie

import com.thuypham.ptithcm.data.remote.api.MovieV3Api
import com.thuypham.ptithcm.data.remote.response.ListResponse
import com.thuypham.ptithcm.data.remote.response.Movie
import com.thuypham.ptithcm.domain.datasource.BaseRemotePagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class NowPlayingPagingSource(private val movieApi: MovieV3Api) : BaseRemotePagingSource<Movie>() {

    override suspend fun createApiCall(page: Int): Response<ListResponse<Movie>> {
        return withContext(Dispatchers.IO) { movieApi.getNowPlaying(page) }
    }

}