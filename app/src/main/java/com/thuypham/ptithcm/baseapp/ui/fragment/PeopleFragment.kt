package com.thuypham.ptithcm.baseapp.ui.fragment

import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.base.BaseFragment
import com.thuypham.ptithcm.baseapp.databinding.FragmentPeopleBinding
import com.thuypham.ptithcm.baseapp.ui.adapter.PeopleAdapter
import com.thuypham.ptithcm.baseapp.util.navigateToPersonDetail
import com.thuypham.ptithcm.baseapp.viewmodel.PeopleViewModel
import com.thuypham.ptithcm.baselib.base.extension.goBack
import org.koin.androidx.viewmodel.ext.android.viewModel

class PeopleFragment : BaseFragment<FragmentPeopleBinding>(R.layout.fragment_people) {

    companion object {
        const val TITLE = "TITLE"
    }

    private val peopleViewModel: PeopleViewModel by viewModel()
    private val peopleAdapter by lazy { PeopleAdapter().initPeopleAdapter(::onPersonItemClick) }

    private var title: String = ""

    private fun onPersonItemClick(position: Int) {
        val person = peopleAdapter.getItemAtPosition(position)
        navigateToPersonDetail(person = person)
    }

    override fun setupFirst() {
        super.setupFirst()
        arguments?.let {
            title = it.getString(TITLE, "")
        }
    }

    override fun getData() {
        showLoading()
        peopleViewModel.getPopularPeople()
    }

    override fun setupView() {
        setupRecyclerView()
    }

    override fun setupToolbar() {
        toolbarHelper.run {
            setToolbarTitle(title)
            setLeftBtn(R.drawable.ic_back) {
                goBack()
            }
        }
    }

    private fun setupRecyclerView() {
        binding.run {
            rvPeople.adapter = peopleAdapter
            rvPeople.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayout.VERTICAL))
        }
    }

    override fun setupDataObserver() {
        peopleViewModel.movieListPaging?.observe(viewLifecycleOwner) { person ->
            hideLoading()
            peopleAdapter.submitData(lifecycle, person)
        }
    }


}