package com.thuypham.ptithcm.baseapp.ui.fragment.movie.detail

import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.databinding.FragmentMovieDetailBinding
import com.thuypham.ptithcm.data.remote.response.MovieDetail


class MovieDetailFragment : BaseDetailFragment<FragmentMovieDetailBinding>(R.layout.fragment_movie_detail) {

    override fun setupView() {
        super.setupView()
        binding.run {

        }
    }

    override fun onDetailMovieResponse(movie: MovieDetail) {

    }
}