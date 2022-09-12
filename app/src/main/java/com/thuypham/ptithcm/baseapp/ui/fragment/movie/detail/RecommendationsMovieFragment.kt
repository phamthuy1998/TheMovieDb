package com.thuypham.ptithcm.baseapp.ui.fragment.movie.detail

import com.thuypham.ptithcm.baseapp.ui.fragment.movie.MovieListBaseFragment
import com.thuypham.ptithcm.baseapp.util.NavConstant
import com.thuypham.ptithcm.baseapp.viewmodel.MovieViewModel
import com.thuypham.ptithcm.baselib.base.extension.logD
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecommendationsMovieFragment : MovieListBaseFragment() {

    private val movieViewModel: MovieViewModel by viewModel()
    private var movieId = 0


    override fun setupFirst() {
        super.setupFirst()
        arguments?.let {
            movieId = it.getInt(NavConstant.MOVIE_ID, 610150)
        }
    }

    override fun getData() {
        logD("get data")
        movieViewModel.getMovieRecommendation(movieId)
    }

    override fun setupDataObserver() {
        movieViewModel.moviesResponse.observe(viewLifecycleOwner) {
            submitRecyclerViewData(it)
        }

        movieViewModel.errorLiveData.observe(viewLifecycleOwner) {
            showSnackBar(it.message)
        }

    }
}