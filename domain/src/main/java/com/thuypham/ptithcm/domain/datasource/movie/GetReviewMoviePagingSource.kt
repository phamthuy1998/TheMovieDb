package com.thuypham.ptithcm.domain.datasource.movie

import com.thuypham.ptithcm.data.remote.api.MovieV3Api
import com.thuypham.ptithcm.data.remote.response.ListResponse
import com.thuypham.ptithcm.data.remote.response.Movie
import com.thuypham.ptithcm.data.remote.response.Review
import com.thuypham.ptithcm.domain.datasource.BaseRemotePagingSource
import retrofit2.Response

class GetReviewMoviePagingSource(private val movieApi: MovieV3Api, private val movieID: Int) : BaseRemotePagingSource<Review>() {
    override suspend fun createApiCall(page: Int): Response<ListResponse<Review>> {
        return movieApi.getMovieReview(movieID, page)
    }

}