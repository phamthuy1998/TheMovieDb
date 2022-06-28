package com.thuypham.ptithcm.domain.repository.impl

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.thuypham.ptithcm.baselib.base.model.ResponseHandler
import com.thuypham.ptithcm.data.remote.api.MovieV3Api
import com.thuypham.ptithcm.data.remote.api.wrapApiCall
import com.thuypham.ptithcm.data.remote.response.*
import com.thuypham.ptithcm.domain.datasource.movie.GetPeoplePagingSource
import com.thuypham.ptithcm.domain.repository.PeopleRepository
import com.thuypham.ptithcm.domain.util.Constant

class PeopleRepositoryImpl(private val movieV3Api: MovieV3Api) : PeopleRepository {
    override suspend fun getPopularPeople(page: Int): ResponseHandler<ListResponse<Person>> {
        return wrapApiCall { movieV3Api.getPopularPeople() }
    }

    override suspend fun getPopularPeoplePaging(): LiveData<PagingData<Person>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = Constant.DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                GetPeoplePagingSource(movieV3Api)
            }
        ).liveData
    }

    override suspend fun getPersonDetail(personId: Int): ResponseHandler<PersonDetail> {
        return wrapApiCall { movieV3Api.getPersonDetail(personId) }
    }

    override suspend fun getPersonImage(personId: Int): ResponseHandler<PersonImage> {
        return wrapApiCall { movieV3Api.getPersonImage(personId) }
    }

    override suspend fun getPersonMovie(personId: Int): ResponseHandler<PersonMovie> {
        return wrapApiCall { movieV3Api.getPersonMovie(personId) }
    }

    override suspend fun getPersonTVShows(personId: Int): ResponseHandler<PersonMovie> {
        return wrapApiCall { movieV3Api.getPersonTVShows(personId) }
    }
}