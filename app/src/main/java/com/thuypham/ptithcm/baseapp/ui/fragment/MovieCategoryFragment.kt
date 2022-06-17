package com.thuypham.ptithcm.baseapp.ui.fragment

import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.base.BaseFragment
import com.thuypham.ptithcm.baseapp.databinding.FragmentMovieCategoryBinding
import com.thuypham.ptithcm.baseapp.ui.adapter.MovieAdapter
import com.thuypham.ptithcm.baseapp.ui.adapter.MovieAdapterVertical
import com.thuypham.ptithcm.baseapp.viewmodel.MovieCategoryViewModel
import com.thuypham.ptithcm.baselib.base.extension.goBack
import com.thuypham.ptithcm.baselib.base.extension.logD
import com.thuypham.ptithcm.baselib.base.extension.show
import com.thuypham.ptithcm.data.local.IStorage
import com.thuypham.ptithcm.data.local.SharedPreferencesStorage
import com.thuypham.ptithcm.data.remote.response.Movie
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class MovieCategoryFragment : BaseFragment<FragmentMovieCategoryBinding>(R.layout.fragment_movie_category) {
    companion object {
        const val CATEGORY_TYPE = "CATEGORY_TYPE"
        const val TITLE = "TITLE"
    }

    private val movieViewModel: MovieCategoryViewModel by viewModel()

    private val movieAdapterGridView by lazy { MovieAdapter().initMovieAdapter() }
    private val movieAdapterLinear by lazy { MovieAdapterVertical().initMovieAdapter() }

    private val sharedPrf: IStorage by inject()

    private var categoryType: String = ""
    private var title: String = ""
    private var firstVisibleItemPosition = 0

    private var isRecyclerviewGridLayout = true

    private val smoothScroller: SmoothScroller by lazy {
        object : LinearSmoothScroller(context) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }
        }
    }

    override fun setupFirst() {
        showLoading()
        arguments?.let {
            categoryType = it.getString(CATEGORY_TYPE, "") ?: ""
            title = it.getString(TITLE, "") ?: ""
        }
        isRecyclerviewGridLayout = sharedPrf.getBoolean(SharedPreferencesStorage.IS_RECYCLERVIEW_LAYOUT_GRID_VIEW, true)
    }

    override fun setupView() {
        setupRecyclerViewByType()

        movieAdapterGridView.addLoadStateListener {
            binding.apply {
                when (it.refresh) {
                    is LoadState.NotLoading -> {
                        hideLoading()
                    }
                    LoadState.Loading -> {
                        showLoading()
                    }

                    is LoadState.Error -> {
                        viewStubEmpty.viewStub?.show()
                        viewStubEmpty.binding
                    }
                }
            }
        }
    }

    private fun setupRecyclerViewAsGridView() {
        binding.rvMovies.run {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = movieAdapterGridView
        }

    }

    private fun setupRecyclerViewAsLinear() {
        binding.rvMovies.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = movieAdapterLinear
        }
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

                sharedPrf.setBoolean(SharedPreferencesStorage.IS_RECYCLERVIEW_LAYOUT_GRID_VIEW, isRecyclerviewGridLayout)
            }
        }
    }

    private fun getIconResource(): Int {
        return if (isRecyclerviewGridLayout) R.drawable.ic_linearlayout_list else R.drawable.ic_gridview_list
    }

    private fun setupRecyclerViewByType() {
        if (isRecyclerviewGridLayout) {
            logD("setupRecyclerViewByType:isRecyclerviewGridLayout")
            (binding.rvMovies.layoutManager as? LinearLayoutManager)?.run {
                firstVisibleItemPosition = findFirstVisibleItemPosition()
            }
            setupRecyclerViewAsGridView()
        } else {
            (binding.rvMovies.layoutManager as? GridLayoutManager)?.run {
                firstVisibleItemPosition = findFirstVisibleItemPosition()
            }
            if (firstVisibleItemPosition > 0) firstVisibleItemPosition += 10
            setupRecyclerViewAsLinear()
        }
        logD("setupRecyclerViewByType: firstVisibleItemPosition: $firstVisibleItemPosition")


        // submit data for recycler view
        movieViewModel.movieListPaging?.value?.let {
            submitRecyclerViewData(it)

            binding.rvMovies.post {
                binding.rvMovies.layoutManager?.scrollToPosition(firstVisibleItemPosition)
            }

        }
    }


    override fun getData() {
        movieViewModel.getMovieItems(categoryType)
    }

    override fun setupDataObserver() {

        movieViewModel.movieListPaging?.observe(viewLifecycleOwner) { movieList ->
            hideLoading()
            submitRecyclerViewData(movieList)
        }

        movieViewModel.errorLiveData.observe(viewLifecycleOwner) {
            showSnackBar(it.message)
        }
    }

    private fun submitRecyclerViewData(movieList: PagingData<Movie>) {
        if (isRecyclerviewGridLayout) {
            movieAdapterGridView.submitData(lifecycle, movieList)
        } else {
            movieAdapterLinear.submitData(lifecycle, movieList)
        }
    }
}