package com.thuypham.ptithcm.baseapp.ui.fragment.movie.list

import androidx.core.view.isVisible
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.base.BaseFragment
import com.thuypham.ptithcm.baseapp.databinding.FragmentMovieListBinding
import com.thuypham.ptithcm.baseapp.ui.adapter.MovieAdapter
import com.thuypham.ptithcm.baseapp.ui.adapter.MovieAdapterVertical
import com.thuypham.ptithcm.baseapp.util.navigateToMovieDetail
import com.thuypham.ptithcm.baselib.base.base.BasePagedAdapter
import com.thuypham.ptithcm.baselib.base.extension.gone
import com.thuypham.ptithcm.baselib.base.extension.logD
import com.thuypham.ptithcm.data.local.IStorage
import com.thuypham.ptithcm.data.local.SharedPreferencesStorage
import com.thuypham.ptithcm.data.remote.response.Movie
import org.koin.android.ext.android.inject

abstract class MoviePagedListBaseFragment : BaseFragment<FragmentMovieListBinding>(R.layout.fragment_movie_list) {

    private val sharedPrf: IStorage by inject()

    private val movieAdapterGridView: BasePagedAdapter<Movie> by lazy {
        MovieAdapter().initMovieAdapter(Glide.with(this)) { position ->
            onItemMovieClick(
                movieAdapterGridView.getItemAtPosition(position)
            )
        }
    }

    private val movieAdapterLinear: BasePagedAdapter<Movie> by lazy {
        MovieAdapterVertical().initMovieAdapter(Glide.with(this)) { position ->
            movieAdapterLinear.getItemAtPosition(
                position
            )
        }
    }

    private fun onItemMovieClick(movie: Movie?) {
        navigateToMovieDetail(movie ?: return)
    }

    private var currentList: PagingData<Movie>? = null

    private var isRecyclerviewGridLayout = true
    private var firstVisibleItemPosition = 0

    override fun setupFirst() {
        super.setupFirst()
        isRecyclerviewGridLayout = sharedPrf.getBoolean(SharedPreferencesStorage.IS_RECYCLERVIEW_LAYOUT_GRID_VIEW, true)
    }

    override fun setupView() {
        updateShimmer(true)
        movieAdapterGridView.addLoadStateListener(loadStateListener)
        movieAdapterLinear.addLoadStateListener(loadStateListener)

        setupRecyclerViewByType()
    }

    private val loadStateListener: (CombinedLoadStates) -> Unit = object : Function1<CombinedLoadStates, Unit> {
        override fun invoke(loadStates: CombinedLoadStates) {
            binding.apply {
                when (loadStates.refresh) {
                    is LoadState.NotLoading -> {
                        updateShimmer(false)
                    }
                    LoadState.Loading -> {
                        updateShimmer(true)
                    }

                    is LoadState.Error -> {
                        updateShimmer(false)
                        showEmptyData(true)
                    }
                }
            }
        }
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

    protected fun submitRecyclerViewData(movieList: PagingData<Movie>?) {
        logD("submitRecyclerViewData")
        currentList = movieList
        updateShimmer(false)
        if (movieList == null) {
            showEmptyData(true)
            return
        }
        showEmptyData(false)
        if (isRecyclerviewGridLayout) {
            movieAdapterGridView.submitData(lifecycle, movieList)
        } else {
            movieAdapterLinear.submitData(lifecycle, movieList)
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
        movieAdapterGridView.removeLoadStateListener(loadStateListener)
        movieAdapterLinear.removeLoadStateListener(loadStateListener)
    }


}