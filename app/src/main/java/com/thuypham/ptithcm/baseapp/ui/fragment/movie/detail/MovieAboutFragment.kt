package com.thuypham.ptithcm.baseapp.ui.fragment.movie.detail

import android.view.LayoutInflater
import android.view.View
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.base.BaseFragment
import com.thuypham.ptithcm.baseapp.databinding.FragmentMovieAboutBinding
import com.thuypham.ptithcm.baseapp.databinding.ItemMovieInfoBinding
import com.thuypham.ptithcm.baseapp.extension.loadImage
import com.thuypham.ptithcm.baseapp.ui.adapter.MovieGenreAdapter
import com.thuypham.ptithcm.baseapp.viewmodel.MovieViewModel
import com.thuypham.ptithcm.baselib.base.extension.*
import com.thuypham.ptithcm.data.remote.response.MovieDetail
import com.thuypham.ptithcm.data.remote.response.MovieImage
import com.thuypham.ptithcm.data.remote.response.MovieVideo
import com.thuypham.ptithcm.data.remote.response.Video
import org.koin.androidx.navigation.koinNavGraphViewModel

class MovieAboutFragment : BaseFragment<FragmentMovieAboutBinding>(R.layout.fragment_movie_about) {

    private val movieViewModel: MovieViewModel by koinNavGraphViewModel(R.id.movie_detail_graph)
    private var isCollapse = true

    private val trailerAdapter by lazy { TrailerAdapter().setupAdapter(glide, ::onItemTrailerClick) }
    private val movieGenreAdapter by lazy { MovieGenreAdapter().setupMovieGenreAdapter() }

    private fun onItemTrailerClick(video: Video) {

    }

    override fun setupDataObserver() {
        movieViewModel.movieDetailResponse.observerData { data -> onDetailMovieResponse(data) }
        movieViewModel.movieImages.observerData { data -> onMovieImagesResponse(data) }
        movieViewModel.movieVideo.observerData { data -> onMovieVideoResponse(data) }
    }

    private fun onMovieVideoResponse(movieVideo: MovieVideo) {
        logD("onMovieVideoResponse: $movieVideo")
        binding.run {
            if (!movieVideo.results.isNullOrEmpty()) {
                tvTrailers.show()
                rvTrailers.show()
                trailerAdapter.submitList(movieVideo.results)
            }

        }
    }

    private fun onDetailMovieResponse(movie: MovieDetail) {
        logD("onDetailMovieResponse")
        movieViewModel.getMovieVideo()

        binding.run {
            tvOverView.text = movie.overview
            movieGenreAdapter.submitList(movie.genres.map { it.name })

            // Movie info
            addDetailViews(movie)

            // Collection
            movie.belongsToCollection?.let {
                ivCollection.loadImage(glide, it.backdropPath)
                frameCollection.show()
                tvCollectionName.text = it.name
            }

        }
    }

    private fun FragmentMovieAboutBinding.addDetailViews(movie: MovieDetail) {
        val result = getMovieInfoListItem(movie)
        layoutMovieInfo.removeAllViews()
        result.forEach {
            val movieInfoBinding = ItemMovieInfoBinding.inflate(LayoutInflater.from(context))
            movieInfoBinding.tvTitle.text = it.first
            movieInfoBinding.tvContent.text = it.second

            layoutMovieInfo.addView(movieInfoBinding.root)
        }
    }

    private fun getMovieInfoListItem(movie: MovieDetail): ArrayList<Pair<String, String>> {
        val result = arrayListOf<Pair<String, String>>()
        result.add(Pair(getString(R.string.original_title), movie.originalTitle))
        result.add(Pair(getString(R.string.status), movie.status))
        result.add(Pair(getString(R.string.run_time), movie.runtime.toMovieDuration(requireContext())))
        result.add(Pair(getString(R.string.original_language), movie.spokenLanguages.findLast { it.iso6391 == movie.originalLanguage }?.englishName ?: ""))
        result.add(Pair(getString(R.string.production_countries), movie.getProductionCountries()))
        result.add(Pair(getString(R.string.company), movie.getProductionCompany()))
        result.add(Pair(getString(R.string.budge), movie.budget.getCurrency()))
        result.add(Pair(getString(R.string.revenue), movie.revenue.getCurrency()))
        return result
    }

    private fun onMovieImagesResponse(movie: MovieImage) {
        binding.run {
            movie.posters?.first()?.let { poster ->
                layoutMedia.show()
                tvPosterCount.text = if (movie.posters!!.size == 1) getString(R.string.onePoster) else getString(R.string.posters, movie.posters!!.size)
                ivPoster.loadImage(glide, poster.filePath)
            }
            movie.backdrops?.first()?.let { poster ->
                layoutMedia.show()
                tvBackdropCount.text = if (movie.backdrops!!.size == 1) getString(R.string.oneBackdrop) else getString(R.string.backdrops, movie.backdrops!!.size)
                ivBackdrop.loadImage(glide, poster.filePath)
            }
        }
    }

    override fun setupView() {
        binding.run {
            tvBtnShowMore.setOnClickListener(biographyClickListener)
            tvOverView.setOnClickListener(biographyClickListener)
            tvGotoCollection.setOnSingleClickListener {
                // Todo
            }

            rvGenres.adapter = movieGenreAdapter
            rvTrailers.adapter = trailerAdapter
        }
    }

    private val biographyClickListener = View.OnClickListener {
        isCollapse = !isCollapse
        binding.run {
            if (isCollapse) {
                tvOverView.maxLines = 3
                tvBtnShowMore.text = getString(R.string.show_more)
            } else {
                tvOverView.maxLines = tvOverView.text.length
                tvBtnShowMore.text = getString(R.string.show_less)
            }
        }
    }


}

