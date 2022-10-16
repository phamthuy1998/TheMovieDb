package com.thuypham.ptithcm.baseapp.ui.fragment.movie.detail

import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.databinding.FragmentMovieDetailBinding
import com.thuypham.ptithcm.baseapp.extension.loadImage
import com.thuypham.ptithcm.baseapp.ui.adapter.MoviePosterAdapter
import com.thuypham.ptithcm.baselib.base.extension.logD
import com.thuypham.ptithcm.baselib.base.extension.milliSecondToDateFormat
import com.thuypham.ptithcm.baselib.base.extension.toMillisecond
import com.thuypham.ptithcm.baselib.base.extension.toMovieDuration
import com.thuypham.ptithcm.data.remote.response.MovieDetail
import com.thuypham.ptithcm.data.remote.response.MovieImage
import kotlin.math.abs


class MovieDetailFragment : BaseDetailFragment<FragmentMovieDetailBinding>(R.layout.fragment_movie_detail) {

    companion object {
        private const val MAX_BACKDROPS_SIZE = 10
    }

    private val moviePosterAdapter by lazy { MoviePosterAdapter().initAdapter(glide) }

    private val appbarOffsetChangeListener by lazy {
        AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val isCollapsed = abs(verticalOffset) >= appBarLayout.totalScrollRange
            binding.toolbar.isVisible = isCollapsed
            binding.ivBack.isVisible = !isCollapsed
            binding.ivPoster.isVisible = !isCollapsed
            binding.tvMovieName.isVisible = !isCollapsed
        }
    }


    override fun setupView() {
        super.setupView()
        binding.run {
            viewPagerBackdrops.adapter = moviePosterAdapter

            TabLayoutMediator(tabIndicator, viewPagerBackdrops) { tab, position ->
            }.attach()

            appBar.addOnOffsetChangedListener(appbarOffsetChangeListener)
        }
    }

    override fun onDetailMovieResponse(movie: MovieDetail) {
        super.onDetailMovieResponse(movie)
        binding.tvMovieName.text = movie.title
        binding.tvDuration.text = movie.runtime.toMovieDuration(requireContext())
        binding.tvMovieDate.text = movie.releaseDate.toMillisecond()?.milliSecondToDateFormat()
        binding.ivPoster.loadImage(glide, movie.posterPath)
    }

    override fun onMovieImagesResponse(movie: MovieImage) {
        logD("onMovieImagesResponse")
        moviePosterAdapter.submitList(movie.backdrops?.map { it.filePath }?.take(MAX_BACKDROPS_SIZE))
    }
}