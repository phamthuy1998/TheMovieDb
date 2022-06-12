package com.thuypham.ptithcm.baseapp.ui.fragment

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.base.BaseFragment
import com.thuypham.ptithcm.baseapp.databinding.FragmentMovieCategoryBinding
import com.thuypham.ptithcm.baseapp.ui.adapter.MovieAdapter
import com.thuypham.ptithcm.baseapp.viewmodel.MovieCategoryViewModel
import com.thuypham.ptithcm.baselib.base.extension.goBack
import com.thuypham.ptithcm.baselib.base.extension.logD
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieCategoryFragment : BaseFragment<FragmentMovieCategoryBinding>(R.layout.fragment_movie_category) {
    companion object {
        const val CATEGORY_TYPE = "CATEGORY_TYPE"
        const val TITLE = "TITLE"
    }

    private val movieViewModel: MovieCategoryViewModel by viewModel()
    private var categoryType: String = ""
    private var title: String = ""
    private var isMovieListLoadMore = false

    private val movieAdapter by lazy {
        MovieAdapter().initMovieAdapter()
    }

    override fun setupFirst() {
        showLoading()
        arguments?.let {
            categoryType = it.getString(CATEGORY_TYPE, "") ?: ""
            title = it.getString(TITLE, "") ?: ""
        }
    }

    override fun setupView() {
        setupRecyclerViewAsGridView()
    }

    private fun setupRecyclerViewAsGridView(){
        binding?.rvMovies?.run {
            layoutManager = GridLayoutManager(requireContext(), 3)
            val recyclerViewLayoutManager = this.layoutManager as GridLayoutManager

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val movieItemLoadingPosition = recyclerViewLayoutManager.findLastCompletelyVisibleItemPosition()
                    logD("OnLoadMore, currentpos: $movieItemLoadingPosition size: ${movieAdapter.itemCount}")

                    if (movieViewModel.isLoadMoreAble && !isMovieListLoadMore && movieAdapter.itemCount % movieItemLoadingPosition <= 5) {
                        logD("OnLoadMore")
                        isMovieListLoadMore = true
                        getData()
                    }
                }
            })

            adapter = movieAdapter
        }
    }

    private fun setupRecyclerViewAsLinear(){

    }

    override fun setupToolbar() {
        toolbarHelper?.run {
            setLeftBtn(R.drawable.ic_back) {
                goBack()
            }
            setToolbarTitle(title)
        }
    }

    override fun getData() {
        movieViewModel.getMovieItems(categoryType)
    }

    override fun setupDataObserver() {
        movieViewModel.movieList.observe(viewLifecycleOwner) { movieList ->
            hideLoading()
            isMovieListLoadMore = false
            if (movieList.isNullOrEmpty()) {

            } else {
                movieAdapter.submitList(movieList)
            }
        }
        movieViewModel.errorLiveData.observe(viewLifecycleOwner) {
            showSnackBar(it.extra)
        }
    }
}