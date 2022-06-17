package com.thuypham.ptithcm.domain.repository

import com.thuypham.ptithcm.baselib.base.model.ResponseHandler
import com.thuypham.ptithcm.data.remote.response.ListResponse
import com.thuypham.ptithcm.data.remote.response.Person

interface PeopleRepository {
    suspend fun getPopularPeople(page: Int): ResponseHandler<ListResponse<Person>>
}