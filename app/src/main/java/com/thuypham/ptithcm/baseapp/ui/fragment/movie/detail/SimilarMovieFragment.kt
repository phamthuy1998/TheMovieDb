package com.thuypham.ptithcm.baseapp.ui.fragment.movie.detail

import com.thuypham.ptithcm.baseapp.ui.fragment.movie.MovieListBaseFragment
import com.thuypham.ptithcm.baseapp.ui.fragment.movie.list.MoviePagedListBaseFragment
import com.thuypham.ptithcm.baseapp.util.NavConstant
import com.thuypham.ptithcm.baseapp.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SimilarMovieFragment : MoviePagedListBaseFragment() {

    private val movieViewModel: MovieViewModel by viewModel()
    private var movieId = 0


    override fun setupFirst() {
        super.setupFirst()
        arguments?.let {
            movieId = it.getInt(NavConstant.MOVIE_ID, 0)
        }
    }

    override fun getData() {
        movieViewModel.getSimilarMovies(movieId)
    }

    override fun setupDataObserver() {
        movieViewModel.moviesResponse.observe(viewLifecycleOwner) {
            submitRecyclerViewData(it)
        }
    }
}