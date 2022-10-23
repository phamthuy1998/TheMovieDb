package com.thuypham.ptithcm.domain.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.thuypham.ptithcm.baselib.base.extension.logD
import com.thuypham.ptithcm.baselib.base.model.ResponseHandler
import com.thuypham.ptithcm.data.remote.api.wrapApiCall
import com.thuypham.ptithcm.data.remote.response.ListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRemotePagingSource<O : Any> : PagingSource<Int, O>() {

    init {
        logD("init -BaseRemotePagingSource")
    }

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, O>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, O> {
        val page = params.key ?: DEFAULT_PAGE_INDEX
        logD("thuyyyyyy -load")
        return try {
            withContext(Dispatchers.IO) {
                when (val response = wrapApiCall { createApiCall(page) }) {
                    is ResponseHandler.Success -> {
                        logD("thuyyyyyy -ResponseHandler.Success - ${response.data.results}")
                        if (shouldCallDataOneTime) {
                            logD("shouldCallDataOneTime")
                            LoadResult.Page(
                                response.data.results ?: throw NullPointerException(),
                                prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
                                nextKey = null
                            )
                        } else {
                            LoadResult.Page(
                                response.data.results ?: throw NullPointerException(),
                                prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
                                nextKey = if (response.data.results?.isEmpty() == true) null else page + 1
                            )

                        }
                    }
                    else -> {
                        logD("thuyyyyyy -ResponseHandler. else")

                        response as ResponseHandler.Error
                        LoadResult.Error(response.error)
                    }
                }
            }
        } catch (exception: IOException) {
            logD("thuyyyyyy -exception")
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            logD("thuyyyyyy -exception")
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            logD("thuyyyyyy -exception")
            return LoadResult.Error(exception)
        }
    }

    protected abstract suspend fun createApiCall(page: Int): Response<ListResponse<O>>
    open var shouldCallDataOneTime = false
}