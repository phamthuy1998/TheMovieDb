package com.thuypham.ptithcm.baseapp.ui.fragment

import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.base.BaseFragment
import com.thuypham.ptithcm.baseapp.databinding.FragmentMovieDetailBinding

class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>(R.layout.fragment_movie_detail) {
    companion object {
        const val MOVIE_ID = "MOVIE_ID"
    }

    private var movieId: Int = 0

    override fun setupFirst() {
        arguments?.let {
            movieId = it.getInt(MOVIE_ID, 0)
        }
    }

    override fun getData() {
        super.getData()

    }

    override fun setupView() {

    }
}