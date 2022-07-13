package com.thuypham.ptithcm.baseapp.ui.fragment

import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.base.BaseFragment
import com.thuypham.ptithcm.baseapp.databinding.FragmentGenresBinding
import com.thuypham.ptithcm.baseapp.model.HomeCategoryType
import com.thuypham.ptithcm.baseapp.ui.adapter.GenreAdapter
import com.thuypham.ptithcm.baseapp.util.navigateToMovieList
import com.thuypham.ptithcm.baseapp.viewmodel.GenreViewModel
import com.thuypham.ptithcm.baselib.base.base.BaseViewAdapter
import com.thuypham.ptithcm.baselib.base.extension.goBack
import com.thuypham.ptithcm.data.remote.response.MovieGenre
import org.koin.androidx.viewmodel.ext.android.viewModel

class GenresFragment : BaseFragment<FragmentGenresBinding>(R.layout.fragment_genres) {
    private val genreViewModel: GenreViewModel by viewModel()
    private val genreAdapter: BaseViewAdapter<MovieGenre> by lazy { GenreAdapter().initGenreAdapter(::onGenreItemClick) }


    override fun getData() {
        genreViewModel.getGenres()
    }

    override fun setupView() {
        binding.run {
            rvGenres.setHasFixedSize(true)
            rvGenres.adapter = genreAdapter
        }
    }

    override fun setupToolbar() {
        toolbarHelper.apply {
            setToolbarTitle(getString(R.string.movie_genres))
            setLeftBtn(R.drawable.ic_back) {
                goBack()
            }
        }
    }


    private fun onGenreItemClick(movieGenre: MovieGenre) {
        navigateToMovieList(HomeCategoryType.MOVIE_GENRES, movieGenre.name, movieGenre.id)
    }

    override fun setupDataObserver() {
        genreViewModel.genres.observe(viewLifecycleOwner) {
            genreAdapter.submitList(it)
        }
    }
}