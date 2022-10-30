package com.thuypham.ptithcm.baseapp.ui.fragment.movie.detail

import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.base.BaseFragment
import com.thuypham.ptithcm.baseapp.databinding.FragmentMovieAboutBinding
import com.thuypham.ptithcm.baseapp.databinding.FragmentMovieCastBinding
import com.thuypham.ptithcm.baseapp.databinding.FragmentMovieCommentBinding
import com.thuypham.ptithcm.baseapp.viewmodel.MovieViewModel
import org.koin.androidx.navigation.koinNavGraphViewModel

class MovieCommentFragment: BaseFragment<FragmentMovieCommentBinding>(R.layout.fragment_movie_comment) {

    private val movieViewModel: MovieViewModel by koinNavGraphViewModel(R.id.movie_detail_graph)

    override fun setupView() {

    }
}