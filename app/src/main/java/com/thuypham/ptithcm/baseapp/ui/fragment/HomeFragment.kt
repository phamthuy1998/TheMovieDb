package com.thuypham.ptithcm.baseapp.ui.fragment

import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.base.BaseFragment
import com.thuypham.ptithcm.baseapp.databinding.FragmentHomeBinding
import com.thuypham.ptithcm.baseapp.ui.adapter.GenreItemViewHolderFactory
import com.thuypham.ptithcm.baseapp.ui.adapter.MovieListItemsViewHolderFactory
import com.thuypham.ptithcm.baseapp.ui.adapter.RecyclerViewMultiTypeAdapter
import com.thuypham.ptithcm.baseapp.viewmodel.HomeViewModel
import com.thuypham.ptithcm.baselib.base.extension.logD
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeAdapter by lazy {
        RecyclerViewMultiTypeAdapter().registerViewHolderFactory(MovieListItemsViewHolderFactory())
    }

    private val homeViewModel: HomeViewModel by viewModel()


    override fun getData() {
        super.getData()
        homeViewModel.getAllDataHome()
    }

    override fun setupView() {
        binding.apply {
            rvMainHome.adapter = homeAdapter
            homeAdapter.setOnItemClick { view, itemModel ->
            }
        }
    }

    override fun setupDataObserver() {
        super.setupDataObserver()
        homeViewModel.homeCategories.observe(viewLifecycleOwner) { homeCategories ->
            logD("homeCategories: $homeCategories")
            if (homeCategories.isNullOrEmpty()) {
                // Show Empty view when homeCategories is empty
            } else {
                homeAdapter.submitList(homeCategories.toList())
            }
        }

        homeViewModel.notifyItemChangePosition.observe(viewLifecycleOwner) { position->
            logD("notifyItemChangePosition: $position")
            homeAdapter.notifyItemChanged(position)
        }
    }
}