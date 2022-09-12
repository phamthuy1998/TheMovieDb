package com.thuypham.ptithcm.baseapp.ui.fragment.movie

import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.base.BaseFragment
import com.thuypham.ptithcm.baseapp.databinding.FragmentMovieListBinding
import com.thuypham.ptithcm.baseapp.ui.adapter.PersonMovieAdapter
import com.thuypham.ptithcm.baseapp.util.navigateToMovieDetail
import com.thuypham.ptithcm.baselib.base.base.BaseViewAdapter
import com.thuypham.ptithcm.baselib.base.extension.gone
import com.thuypham.ptithcm.baselib.base.extension.logD
import com.thuypham.ptithcm.data.local.IStorage
import com.thuypham.ptithcm.data.local.SharedPreferencesStorage
import com.thuypham.ptithcm.data.remote.response.Movie
import org.koin.android.ext.android.inject

abstract class MovieListBaseFragment : BaseFragment<FragmentMovieListBinding>(R.layout.fragment_movie_list) {

    private val sharedPrf: IStorage by inject()

    private val personAdapter by lazy { PersonMovieAdapter() }
    private val movieAdapterGridView: BaseViewAdapter<Movie> by lazy {
        personAdapter.initGridMovieAdapter(Glide.with(this)) { position -> onItemMovieClick(movieAdapterGridView.getItemAtPos(position)) }
    }

    private val movieAdapterLinear: BaseViewAdapter<Movie> by lazy {
        personAdapter.initVerticalMovieAdapter(Glide.with(this)) { position -> onItemMovieClick(movieAdapterLinear.getItemAtPos(position)) }
    }

    private fun onItemMovieClick(movie: Movie?) {
        navigateToMovieDetail(movie?: return)
    }

    private var currentList: List<Movie>? = null

    private var isRecyclerviewGridLayout = true
    private var firstVisibleItemPosition = 0

    override fun setupFirst() {
        super.setupFirst()
        isRecyclerviewGridLayout = sharedPrf.getBoolean(SharedPreferencesStorage.IS_RECYCLERVIEW_LAYOUT_GRID_VIEW, true)
    }

    override fun setupView() {
        updateShimmer(true)
        setupRecyclerViewByType()
    }

    private fun setupRecyclerViewByType() {
        logD("setupRecyclerViewByType:isRecyclerviewGridLayout: $isRecyclerviewGridLayout")
        if (isRecyclerviewGridLayout) {
            (binding.rvMovies.layoutManager as? LinearLayoutManager)?.run {
                firstVisibleItemPosition = findFirstVisibleItemPosition()
            }
            binding.rvMovies.run {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(requireContext(), 3)
                adapter = movieAdapterGridView
            }

        } else {
            (binding.rvMovies.layoutManager as? GridLayoutManager)?.run {
                firstVisibleItemPosition = findFirstVisibleItemPosition()
            }
            if (firstVisibleItemPosition > 0) firstVisibleItemPosition += 10
            binding.rvMovies.run {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
                adapter = movieAdapterLinear
            }

        }
        logD("setupRecyclerViewByType: firstVisibleItemPosition: $firstVisibleItemPosition")


        // submit data for recycler view
        currentList?.let {
            logD("setupRecyclerViewByType - movieListPaging")
            updateShimmer(false)
            submitRecyclerViewData(it)
            // Scroll to position
            binding.rvMovies.post {
                binding.rvMovies.layoutManager?.scrollToPosition(firstVisibleItemPosition)
            }
        }

    }

    protected fun submitRecyclerViewData(movieList: List<Movie>?) {
        logD("submitRecyclerViewData")
        currentList = movieList
        updateShimmer(false)
        if (movieList.isNullOrEmpty()) {
            showEmptyData(true)
            return
        }
        showEmptyData(false)
        if (isRecyclerviewGridLayout) {
            movieAdapterGridView.submitList(movieList)
        } else {
            movieAdapterLinear.submitList(movieList)
        }
    }

    private fun showEmptyData(visible: Boolean) {
        binding.layoutEmpty.root.isVisible = visible
    }

    private fun updateShimmer(isShow: Boolean) {
        logD("updateShimmer - isShow: $isShow")
        runOnUiThread {
            if (isRecyclerviewGridLayout) {
                binding.layoutGridViewShimmer.root.isVisible = isShow
                binding.layoutVerticalShimmer.root.gone()
            } else {
                binding.layoutGridViewShimmer.root.gone()
                binding.layoutVerticalShimmer.root.isVisible = isShow
            }
        }
    }

    private fun getIconResource(): Int {
        return if (isRecyclerviewGridLayout) R.drawable.ic_linearlayout_list else R.drawable.ic_gridview_list
    }


    override fun clearData() {
        super.clearData()
        binding.rvMovies.adapter = null
    }


}