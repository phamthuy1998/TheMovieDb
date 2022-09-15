package com.thuypham.ptithcm.domain.repository.impl

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.thuypham.ptithcm.baselib.base.extension.logD
import com.thuypham.ptithcm.baselib.base.model.ResponseHandler
import com.thuypham.ptithcm.data.remote.api.MovieV3Api
import com.thuypham.ptithcm.data.remote.api.wrapApiCall
import com.thuypham.ptithcm.data.remote.response.ListResponse
import com.thuypham.ptithcm.data.remote.response.Movie
import com.thuypham.ptithcm.data.remote.response.MovieDetail
import com.thuypham.ptithcm.data.remote.response.MovieGenres
import com.thuypham.ptithcm.domain.datasource.movie.*
import com.thuypham.ptithcm.domain.repository.MovieRepository
import com.thuypham.ptithcm.domain.util.Constant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepositoryImpl(private val movieAPI: MovieV3Api) : MovieRepository {
    override suspend fun getMovieGenres(): ResponseHandler<MovieGenres> {
        return withContext(Dispatchers.IO) {
            wrapApiCall { movieAPI.getMovieGenres() }
        }
    }

    override suspend fun getMovieTrending(page: Int): ResponseHandler<ListResponse<Movie>> {
        return withContext(Dispatchers.IO) {
            wrapApiCall { movieAPI.getMovieTrending(page) }
        }

    }

    override suspend fun getMovieNowPlaying(page: Int): ResponseHandler<ListResponse<Movie>> {
        return withContext(Dispatchers.IO) {
            wrapApiCall { movieAPI.getNowPlaying(page) }
        }
    }

    override suspend fun getMovieUpComing(page: Int): ResponseHandler<ListResponse<Movie>> {
        return withContext(Dispatchers.IO) {
            wrapApiCall { movieAPI.getMovieUpComing(page) }
        }
    }

    override suspend fun getMoviePopular(page: Int): ResponseHandler<ListResponse<Movie>> {
        return withContext(Dispatchers.IO) {
            wrapApiCall { movieAPI.getMoviePopular(page) }
        }
    }

    override suspend fun getMovieTopRate(page: Int): ResponseHandler<ListResponse<Movie>> {
        return withContext(Dispatchers.IO) {
            wrapApiCall { movieAPI.getMovieTopRate(page) }
        }
    }

    override suspend fun getMovieTrendingPaging(): LiveData<PagingData<Movie>> {
        logD("getMovieTrendingPaging")
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = Constant.DEFAULT_PAGE_SIZE),
            initialKey = 1,
            pagingSourceFactory = {
                GetMovieTrendingPagingSource(movieAPI)
            }
        ).liveData
    }

    override suspend fun getMovieNowPlayingPaging(): LiveData<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = Constant.DEFAULT_PAGE_SIZE),
            initialKey = 1,
            pagingSourceFactory = {
                NowPlayingPagingSource(movieAPI)
            }
        ).liveData
    }

    override suspend fun getMovieUpComingPaging(): LiveData<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = Constant.DEFAULT_PAGE_SIZE),
            initialKey = 1,
            pagingSourceFactory = {
                GetUpComingPagingSource(movieAPI)
            }
        ).liveData
    }

    override suspend fun getMoviePopularPaging(): LiveData<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = Constant.DEFAULT_PAGE_SIZE),
            initialKey = 1,
            pagingSourceFactory = {
                GetMoviePopularPagingSource(movieAPI)
            }
        ).liveData
    }

    override suspend fun getMovieTopRatePaging(): LiveData<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = Constant.DEFAULT_PAGE_SIZE),
            initialKey = 1,
            pagingSourceFactory = {
                GetTopRateMoviePagingSource(movieAPI)
            }
        ).liveData
    }

    override suspend fun getMovieByGenreId(genreId: Int): LiveData<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = Constant.DEFAULT_PAGE_SIZE), initialKey = 1,
            pagingSourceFactory = {
                GetMovieByGenreIdPagingSource(movieAPI, genreId)
            }
        ).liveData
    }

    override suspend fun getMovieDetail(movieID: Int): ResponseHandler<MovieDetail> {
        return withContext(Dispatchers.IO) {
            wrapApiCall { movieAPI.getMovieDetail(movieID) }
        }
    }

    override suspend fun getMoviesRecommendation(movieID: Int): ResponseHandler<ListResponse<Movie>> {
        return withContext(Dispatchers.IO) {
            wrapApiCall {
                movieAPI.getMoviesRecommendation(movieID)
            }
        }
    }

    override suspend fun getMoviesRecommendationPaging(movieID: Int): LiveData<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = Constant.DEFAULT_PAGE_SIZE),
            initialKey = 1,
            pagingSourceFactory = {
                GetRecommendationMoviePagingSource(movieAPI, movieID)
            }
        ).liveData
    }

    override suspend fun getSimilarMoviesPaging(movieID: Int): LiveData<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = Constant.DEFAULT_PAGE_SIZE),
            initialKey = 1,
            pagingSourceFactory = {
                GetSimilarMoviePagingSource(movieAPI, movieID)
            }
        ).liveData

    }

    override suspend fun getSimilarMovies(movieID: Int): ResponseHandler<ListResponse<Movie>> {
        return withContext(Dispatchers.IO) {
            wrapApiCall { movieAPI.getSimilarMovies(movieID) }
        }
    }
}