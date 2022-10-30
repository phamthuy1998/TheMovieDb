package com.thuypham.ptithcm.baseapp.ui.fragment.movie.detail

import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.ui.fragment.movie.list.MoviePagedListBaseFragment
import com.thuypham.ptithcm.baseapp.util.NavConstant
import com.thuypham.ptithcm.baseapp.viewmodel.MovieViewModel
import org.koin.androidx.navigation.koinNavGraphViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SimilarMovieFragment : MoviePagedListBaseFragment() {

    private val movieViewModel: MovieViewModel by koinNavGraphViewModel(R.id.movie_detail_graph)

    override val emptyMessage: Pair<String, String> by lazy {
        Pair(
            getString(R.string.empty_msg, getString(R.string.movie_tab_layout_similar)),
            getString(R.string.empty_msg_detail, getString(R.string.movie_tab_layout_similar))
        )
    }

    override fun getData() {
        movieViewModel.getSimilarMovies()
    }

    override fun setupDataObserver() {
        movieViewModel.moviesResponse.observe(viewLifecycleOwner) {
            submitRecyclerViewData(it)
        }
    }
}