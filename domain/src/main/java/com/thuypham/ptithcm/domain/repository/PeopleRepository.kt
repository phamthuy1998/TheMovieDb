package com.thuypham.ptithcm.domain.repository

import com.thuypham.ptithcm.data.remote.response.PopularPeople
import com.thuypham.ptithcm.baselib.base.model.ResponseHandler

interface PeopleRepository {
    suspend fun getPopularPeople(page: Int): ResponseHandler<PopularPeople>
}