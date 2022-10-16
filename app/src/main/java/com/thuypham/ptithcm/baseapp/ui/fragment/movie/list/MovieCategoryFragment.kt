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
import com.thuypham.ptithcm.baseapp.databinding.FragmentMovieCategoryBinding
import com.thuypham.ptithcm.baseapp.ui.adapter.MovieAdapter
import com.thuypham.ptithcm.baseapp.ui.adapter.MovieAdapterVertical
import com.thuypham.ptithcm.baseapp.ui.adapter.PagingStateAdapter
import com.thuypham.ptithcm.baseapp.util.navigateToMovieDetail
import com.thuypham.ptithcm.baseapp.viewmodel.MovieCategoryViewModel
import com.thuypham.ptithcm.baselib.base.base.BasePagedAdapter
import com.thuypham.ptithcm.baselib.base.extension.goBack
import com.thuypham.ptithcm.baselib.base.extension.gone
import com.thuypham.ptithcm.baselib.base.extension.logD
import com.thuypham.ptithcm.data.remote.response.Movie
import org.koin.androidx.viewmodel.ext.android.viewModel


class MovieCategoryFragment : BaseFragment<FragmentMovieCategoryBinding>(R.layout.fragment_movie_category) {
    companion object {
        const val CATEGORY_TYPE = "CATEGORY_TYPE"
        const val TITLE = "TITLE"
        const val GENRE_ID = "GENRE_ID"
    }
    // Todo: Fix bug paging 3: sometimes not load data

    private val movieViewModel: MovieCategoryViewModel by viewModel()

    private val movieAdapterGridView: BasePagedAdapter<Movie> by lazy {
        MovieAdapter().initMovieAdapter(glide) { position ->
            onItemMovieClick(
                movieAdapterGridView.getItemAtPosition(position)
            )
        }
    }

    private fun onItemMovieClick(movie: Movie?) {
        navigateToMovieDetail(movie ?: return)
    }

    private val movieAdapterLinear: BasePagedAdapter<Movie> by lazy {
        MovieAdapterVertical().initMovieAdapter(glide) { position ->
            movieAdapterLinear.getItemAtPosition(
                position
            )
        }
    }

    private var categoryType: String = ""
    private var genreID: Int = 0
    private var title: String = ""
    private var firstVisibleItemPosition = 0

    private var isRecyclerviewGridLayout = true

    override fun setupFirst() {
        arguments?.let {
            categoryType = it.getString(CATEGORY_TYPE, "") ?: ""
            genreID = it.getInt(GENRE_ID, 0)
            title = it.getString(TITLE, "") ?: ""
        }
        isRecyclerviewGridLayout = movieViewModel.isShowGridLayout()
    }

    override fun getData() {
        logD("getData")
        updateShimmer(true)
        if (movieViewModel.movieListPaging.value == null) {
            logD("getData - getMovieItems: categoryType: $categoryType, genreID: $genreID")
            movieViewModel.getMovieItems(categoryType, genreID)
        }
    }


    override fun setupView() {
        logD("setupView")
        setupRecyclerViewByType()

        movieAdapterGridView.addLoadStateListener(loadStateListener)
        movieAdapterLinear.addLoadStateListener(loadStateListener)
    }

    override fun setupToolbar() {
        toolbarHelper.run {
            // Setup back button
            setLeftBtn(R.drawable.ic_back) {
                goBack()
            }

            setToolbarTitle(title)

            // Setup btn select recycler view mode
            setRightBtn(getIconResource()) {
                isRecyclerviewGridLayout = !isRecyclerviewGridLayout

                // update icon
                updateRightBtnIconResource(getIconResource())

                setupRecyclerViewByType()

                movieViewModel.saveRecyclerViewMode(isRecyclerviewGridLayout)
            }
        }
    }

    private fun getIconResource(): Int {
        return if (isRecyclerviewGridLayout) R.drawable.ic_linearlayout_list else R.drawable.ic_gridview_list
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
                    .withLoadStateHeaderAndFooter(
                        footer = PagingStateAdapter { movieAdapterGridView.retry() },
                        header = PagingStateAdapter { movieAdapterGridView.retry() }
                    )
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
                    .withLoadStateHeaderAndFooter(
                        footer = PagingStateAdapter { movieAdapterGridView.retry() },
                        header = PagingStateAdapter { movieAdapterGridView.retry() }
                    )
            }

        }
        logD("setupRecyclerViewByType: firstVisibleItemPosition: $firstVisibleItemPosition")


        // submit data for recycler view
        movieViewModel.movieListPaging.value?.let {
            logD("setupRecyclerViewByType - movieListPaging")
            updateShimmer(false)
            submitRecyclerViewData(it)
            // Scroll to position
            binding.rvMovies.post {
                binding.rvMovies.layoutManager?.scrollToPosition(firstVisibleItemPosition)
            }
        }

    }


    override fun setupDataObserver() {
        logD("setupDataObserver")

        movieViewModel.movieListPaging.observe(this) { movieList ->
            logD("setupDataObserver - movieListPaging")
            updateShimmer(false)
            if (movieList == null) {
                showEmptyData(true)
            } else {
                showEmptyData(false)
                submitRecyclerViewData(movieList)
            }
        }

        movieViewModel.errorLiveData.observe(viewLifecycleOwner) {
            showSnackBar(it.message)
        }
    }

    private fun submitRecyclerViewData(movieList: PagingData<Movie>) {
        logD("submitRecyclerViewData")
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
        logD("updateShimmer - isShow: $isShow isRecyclerviewGridLayout: $isRecyclerviewGridLayout")
        if (isRecyclerviewGridLayout) {
            runOnUiThread {
                binding.layoutGridViewShimmer.root.isVisible = isShow
                binding.layoutVerticalShimmer.root.gone()
            }
        } else {
            runOnUiThread {
                binding.layoutGridViewShimmer.root.gone()
                binding.layoutVerticalShimmer.root.isVisible = isShow
            }
        }
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


    override fun clearData() {
        super.clearData()
        binding.rvMovies.adapter = null

        movieAdapterGridView.removeLoadStateListener(loadStateListener)
        movieAdapterLinear.removeLoadStateListener(loadStateListener)
    }

}