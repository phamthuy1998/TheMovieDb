package com.thuypham.ptithcm.baseapp.ui.fragment

import androidx.core.os.bundleOf
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.base.BaseFragment
import com.thuypham.ptithcm.baseapp.databinding.FragmentHomeBinding
import com.thuypham.ptithcm.baseapp.model.HomeCategoryData
import com.thuypham.ptithcm.baseapp.ui.adapter.HomeCategoryAdapter
import com.thuypham.ptithcm.baseapp.viewmodel.HomeViewModel
import com.thuypham.ptithcm.baselib.base.extension.logD
import com.thuypham.ptithcm.baselib.base.extension.navigateTo
import com.thuypham.ptithcm.data.remote.response.Movie
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.ref.WeakReference


class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModel()

    private val homeAdapter by lazy {
        HomeCategoryAdapter().initHomeCategoryAdapter(::onCategoryClick, ::onChildItemClick)
    }

    override fun getData() {
        homeViewModel.getAllDataHome(WeakReference(requireContext()))
    }

    override fun setupView() {
        binding.run {
            rvMainHome.adapter = homeAdapter
        }
    }

    private fun onCategoryClick(item: Any) {
        (item as? HomeCategoryData)?.apply {
            when (item.listItems?.first()) {
                is Movie -> {
                    navigateTo(
                        R.id.movieCategoryFragment,
                        bundleOf(MovieCategoryFragment.CATEGORY_TYPE to item.type, MovieCategoryFragment.TITLE to item.title)
                    )
                }
            }
        }
        when (item) {
            is HomeCategoryData -> {

            }
        }
    }

    private fun onChildItemClick(item: Any) {

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
}