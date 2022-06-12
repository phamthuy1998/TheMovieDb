package com.thuypham.ptithcm.domain.repository.impl

import com.thuypham.ptithcm.data.remote.api.MovieV3Api
import com.thuypham.ptithcm.data.remote.response.PopularPeople
import com.thuypham.ptithcm.data.remote.api.wrapApiCall
import com.thuypham.ptithcm.domain.repository.PeopleRepository
import com.thuypham.ptithcm.baselib.base.model.ResponseHandler

class PeopleRepositoryImpl(private val movieV3Api: MovieV3Api): PeopleRepository {
    override suspend fun getPopularPeople(page: Int): ResponseHandler<PopularPeople> {
        return wrapApiCall { movieV3Api.getPopularPeople() }
    }
}