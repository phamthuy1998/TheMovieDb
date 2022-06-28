package com.thuypham.ptithcm.domain.usecase

import com.thuypham.ptithcm.domain.repository.PeopleRepository
import com.thuypham.ptithcm.baseapp.domain.usecase.BaseUseCase
import com.thuypham.ptithcm.baselib.base.model.ResponseHandler
import com.thuypham.ptithcm.data.remote.response.ListResponse
import com.thuypham.ptithcm.data.remote.response.Person

class GetPopularPeopleUseCase(private val repository: PeopleRepository) :
    BaseUseCase<Int, ResponseHandler<ListResponse<Person>>>() {

    override suspend fun invoke(param: Int): ResponseHandler<ListResponse<Person>> {
        return repository.getPopularPeople(param)
    }

}