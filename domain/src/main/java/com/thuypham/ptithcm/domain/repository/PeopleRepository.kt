package com.thuypham.ptithcm.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.thuypham.ptithcm.baselib.base.model.ResponseHandler
import com.thuypham.ptithcm.data.remote.response.*

interface PeopleRepository {
    suspend fun getPopularPeople(page: Int): ResponseHandler<ListResponse<Person>>
    suspend fun getPopularPeoplePaging(): LiveData<PagingData<Person>>

    suspend fun getPersonDetail(personId: Int): ResponseHandler<PersonDetail>
    suspend fun getPersonImage(personId: Int): ResponseHandler<PersonImage>
    suspend fun getPersonMovie(personId: Int): ResponseHandler<PersonMovie>
    suspend fun getPersonTVShows(personId: Int): ResponseHandler<PersonMovie>
}