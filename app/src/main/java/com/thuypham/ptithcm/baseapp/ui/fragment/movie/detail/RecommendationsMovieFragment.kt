package com.thuypham.ptithcm.baseapp.ui.fragment.movie.detail

import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.ui.fragment.movie.list.MoviePagedListBaseFragment
import com.thuypham.ptithcm.baseapp.viewmodel.MovieViewModel
import com.thuypham.ptithcm.baselib.base.extension.logD
import org.koin.androidx.navigation.koinNavGraphViewModel

class RecommendationsMovieFragment : MoviePagedListBaseFragment() {

    private val movieViewModel: MovieViewModel by koinNavGraphViewModel(R.id.movie_detail_graph)
    override val emptyMessage: Pair<String, String> by lazy {
        Pair(
            getString(R.string.empty_msg, getString(R.string.movie_tab_layout_recommendations)),
            getString(R.string.empty_msg_detail, getString(R.string.movie_tab_layout_recommendations))
        )
    }

    override fun getData() {
        logD("get data")
        movieViewModel.getMovieRecommendation()
    }

    override fun setupDataObserver() {
        movieViewModel.moviesResponse.observe(viewLifecycleOwner) {
            logD("movieViewModel.moviesResponse: submitRecyclerViewData")
            submitRecyclerViewData(it)
        }

        movieViewModel.errorLiveData.observe(viewLifecycleOwner) {
            showSnackBar(it.message)
        }

    }
}