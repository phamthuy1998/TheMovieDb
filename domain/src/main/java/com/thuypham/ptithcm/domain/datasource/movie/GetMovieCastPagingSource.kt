package com.thuypham.ptithcm.domain.datasource.movie

import com.thuypham.ptithcm.data.remote.api.MovieV3Api
import com.thuypham.ptithcm.data.remote.response.ListResponse
import com.thuypham.ptithcm.data.remote.response.Person
import com.thuypham.ptithcm.domain.datasource.BaseRemotePagingSource
import retrofit2.Response

class GetMovieCastPagingSource(private val movieApi: MovieV3Api, private val movieID: Int) : BaseRemotePagingSource<Person>() {
    override suspend fun createApiCall(page: Int): Response<ListResponse<Person>> {
        return movieApi.getMovieCast(movieID, page)
    }

    override var shouldCallDataOneTime: Boolean = true
}