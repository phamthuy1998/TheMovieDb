package com.thuypham.ptithcm.domain.usecase.people

import com.thuypham.ptithcm.data.remote.response.PopularPeople
import com.thuypham.ptithcm.domain.repository.PeopleRepository
import com.thuypham.ptithcm.baseapp.domain.usecase.BaseUseCase
import com.thuypham.ptithcm.baselib.base.model.ResponseHandler

class GetPopularPeopleUseCase(private val repository: PeopleRepository) :
    BaseUseCase<Int, ResponseHandler<PopularPeople>>() {

    override suspend fun invoke(param: Int): ResponseHandler<PopularPeople> {
        return repository.getPopularPeople(param)
    }

}