package com.thuypham.ptithcm.baseapp.ui.fragment.movie.detail

import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.base.BaseFragment
import com.thuypham.ptithcm.baseapp.databinding.FragmentMovieReviewBinding
import com.thuypham.ptithcm.baseapp.ui.adapter.MovieReviewAdapter
import com.thuypham.ptithcm.baseapp.viewmodel.MovieViewModel
import org.koin.androidx.navigation.koinNavGraphViewModel

class MovieReviewFragment : BaseFragment<FragmentMovieReviewBinding>(R.layout.fragment_movie_review) {

    private val movieViewModel: MovieViewModel by koinNavGraphViewModel(R.id.movie_detail_graph)
    private val reviewAdapter by lazy { MovieReviewAdapter().initAdapter(::onReviewItemClick) }

    private fun onReviewItemClick(position: Int) {
//        val review = reviewAdapter.getItemAtPosition(position)
    }

    override fun setupView() {
        showLoading()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.apply {
            rvReview.adapter = reviewAdapter
        }
    }

    override fun getData() {
        movieViewModel.getMovieReview()
    }

    override fun setupDataObserver() {
        super.setupDataObserver()
        movieViewModel.movieReview.observe(viewLifecycleOwner) {reviewList->
            hideLoading()
            reviewAdapter.submitData(lifecycle, reviewList)
        }

        movieViewModel.errorLiveData.observe(viewLifecycleOwner) {
            showSnackBar(it.message)
        }
    }
}