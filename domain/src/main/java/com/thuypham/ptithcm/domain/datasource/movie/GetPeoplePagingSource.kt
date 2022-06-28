package com.thuypham.ptithcm.domain.datasource.movie

import com.thuypham.ptithcm.data.remote.api.MovieV3Api
import com.thuypham.ptithcm.data.remote.response.ListResponse
import com.thuypham.ptithcm.data.remote.response.Person
import com.thuypham.ptithcm.domain.datasource.BaseRemotePagingSource
import retrofit2.Response

class GetPeoplePagingSource(private val movieApi: MovieV3Api) : BaseRemotePagingSource<Person>() {
    override suspend fun createApiCall(page: Int): Response<ListResponse<Person>> {
        return movieApi.getPopularPeople(page)
    }

}