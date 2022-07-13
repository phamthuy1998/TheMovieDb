package com.thuypham.ptithcm.baseapp.ui.fragment

import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.base.BaseFragment
import com.thuypham.ptithcm.baseapp.databinding.FragmentMovieDetailBinding
import com.thuypham.ptithcm.baseapp.util.NavConstant

class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>(R.layout.fragment_movie_detail) {

    private var movieId: Int = 0

    override fun setupFirst() {
        arguments?.let {
            movieId = it.getInt(NavConstant.MOVIE_ID, 0)
        }
    }

    override fun getData() {
        super.getData()

    }

    override fun setupView() {

    }
}