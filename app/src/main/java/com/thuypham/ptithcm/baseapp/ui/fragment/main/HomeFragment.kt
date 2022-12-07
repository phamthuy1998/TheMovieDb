package com.thuypham.ptithcm.baseapp.ui.fragment.main

import com.bumptech.glide.Glide
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.base.BaseFragment
import com.thuypham.ptithcm.baseapp.databinding.FragmentHomeBinding
import com.thuypham.ptithcm.baseapp.model.HomeCategoryData
import com.thuypham.ptithcm.baseapp.model.HomeCategoryType
import com.thuypham.ptithcm.baseapp.ui.adapter.HomeCategoryAdapter
import com.thuypham.ptithcm.baseapp.util.*
import com.thuypham.ptithcm.baseapp.viewmodel.HomeViewModel
import com.thuypham.ptithcm.baselib.base.extension.*
import com.thuypham.ptithcm.data.remote.response.Movie
import com.thuypham.ptithcm.data.remote.response.MovieGenre
import com.thuypham.ptithcm.data.remote.response.Person
import org.koin.androidx.navigation.koinNavGraphViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.ref.WeakReference


class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by koinNavGraphViewModel(R.id.main_graph)

    private val homeAdapter by lazy {
        HomeCategoryAdapter().initHomeCategoryAdapter(::onCategoryClick, ::onChildItemClick, glide)
    }

    override fun getData() {
        if (isNetworkConnected()) {
            showNoNetWorkLayout(false)
            if (homeViewModel.homeCategories.value.isNullOrEmpty()) {
                homeViewModel.getAllDataHome(WeakReference(requireContext()))
            }
        } else {
            showNoNetWorkLayout(true)
        }
    }

    private fun showNoNetWorkLayout(visible: Boolean) {
        binding.viewStubNoNetWork.viewStub?.run {
            if (visible) {
                inflate()
                show()
            } else {
                gone()
            }
        }
    }

    override fun setupView() {
        binding.run {
            homeAdapter.hashmapScrollPosition = homeViewModel.hashmapScrollPosition
            rvMainHome.adapter = homeAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        homeViewModel.hashmapScrollPosition = homeAdapter.hashmapScrollPosition
    }

    private fun onCategoryClick(item: Any) {
        (item as? HomeCategoryData)?.apply {
            when (item.listItems?.first()) {
                is Movie -> {
                    navigateToMovieList(type, title)
                }
                is MovieGenre -> {
                    navigateTo(R.id.genresFragment)
                }
                is Person -> {
                    navigateToPeople(title)
                }
            }
        }
    }

    private fun onChildItemClick(item: Any) {
        when (item) {
            is Movie -> {
                navigateToMovieDetail(item)
            }
            is MovieGenre -> {
                navigateToMovieList(HomeCategoryType.MOVIE_GENRES, item.name, item.id)
            }
            is Person -> {
                navigateToPersonDetail(person = item)
            }
        }
    }

    override fun setupToolbar() {
        super.setupToolbar()
        toolbarHelper.setToolbarTitle(getString(R.string.app_name))
        toolbarHelper.setImgCenter(R.drawable.ic_logo)
    }

    override fun setupDataObserver() {
        super.setupDataObserver()
        homeViewModel.homeCategories.observe(viewLifecycleOwner) { homeCategories ->
            logD("homeCategories: $homeCategories")
            if (homeCategories.isNullOrEmpty()) {
                // Show Empty view when homeCategories is empty
            } else {
                homeAdapter.submitList(homeCategories.toList())
                homeAdapter.notifyDataSetChanged()
            }
        }

    }

    override fun clearData() {
        super.clearData()
        binding.rvMainHome.adapter = null
    }

}