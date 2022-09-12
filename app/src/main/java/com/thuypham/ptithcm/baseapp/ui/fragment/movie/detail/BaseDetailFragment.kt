package com.thuypham.ptithcm.baseapp.ui.fragment.movie.detail

import androidx.databinding.ViewDataBinding
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.base.BaseFragment
import com.thuypham.ptithcm.baseapp.ui.adapter.MovieDetailPagerAdapter
import com.thuypham.ptithcm.baseapp.util.NavConstant
import com.thuypham.ptithcm.baseapp.viewmodel.MovieViewModel
import com.thuypham.ptithcm.baselib.base.model.ResponseHandler
import com.thuypham.ptithcm.data.remote.response.MovieDetail
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class BaseDetailFragment<T : ViewDataBinding>(layoutId: Int) : BaseFragment<T>(layoutId) {

    protected var movieId: Int = 0
    protected val movieViewModel: MovieViewModel by viewModel()

    private lateinit var pagerAdapter: MovieDetailPagerAdapter
    private val tabTitles by lazy {
        arrayListOf(
            getString(R.string.movie_tab_layout_about),
            getString(R.string.movie_tab_layout_cast),
            getString(R.string.movie_tab_layout_comment),
            getString(R.string.movie_tab_layout_review),
            getString(R.string.movie_tab_layout_recommendations),
            getString(R.string.movie_tab_layout_similar),
        )
    }


    override fun setupFirst() {
        arguments?.let {
            movieId = it.getInt(NavConstant.MOVIE_ID, 0)
        }
    }

    override fun getData() {
        movieViewModel.getMovieDetail(movieId)
    }


    private fun setupViewPagerWithTabLayout() {
        binding.run {
            pagerAdapter = MovieDetailPagerAdapter(movieId, childFragmentManager, viewLifecycleOwner.lifecycle)
            val viewPager = root.findViewById<ViewPager2>(R.id.viewPager)
            val tabLayout = root.findViewById<TabLayout>(R.id.tabLayout)


            viewPager.adapter = pagerAdapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabTitles[position]
            }.attach()
        }
    }


    override fun setupView() {
        setupViewPagerWithTabLayout()
    }

    override fun setupDataObserver() {
        movieViewModel.movieDetail.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResponseHandler.Loading -> showLoading()
                is ResponseHandler.Error -> {
                    hideLoading()
                    showSnackBar(result.message)
                }
                is ResponseHandler.Success -> {
                    hideLoading()
                    onDetailMovieResponse(result.data)
                }
            }
        }
        movieViewModel.errorLiveData
    }

    protected open fun onDetailMovieResponse(movie: MovieDetail) {}
}