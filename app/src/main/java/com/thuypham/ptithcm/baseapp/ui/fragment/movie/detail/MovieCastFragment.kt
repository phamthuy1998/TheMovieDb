package com.thuypham.ptithcm.baseapp.ui.fragment.movie.detail

import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.base.BaseFragment
import com.thuypham.ptithcm.baseapp.databinding.FragmentMovieCastBinding
import com.thuypham.ptithcm.baseapp.ui.adapter.PeopleAdapter
import com.thuypham.ptithcm.baseapp.util.navigateToPersonDetail
import com.thuypham.ptithcm.baseapp.viewmodel.MovieViewModel
import com.thuypham.ptithcm.baselib.base.extension.logD
import org.koin.androidx.navigation.koinNavGraphViewModel

class MovieCastFragment : BaseFragment<FragmentMovieCastBinding>(R.layout.fragment_movie_cast) {

    private val movieViewModel: MovieViewModel by koinNavGraphViewModel(R.id.movie_detail_graph)
    private val peopleAdapter by lazy { PeopleAdapter().initPeopleAdapter(glide, ::onPersonItemClick) }

    private fun onPersonItemClick(position: Int) {
        val person = peopleAdapter.getItemAtPosition(position)
        navigateToPersonDetail(person = person)
    }

    override fun setupView() {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.run {
            rvPeople.adapter = peopleAdapter
//            rvPeople.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//            rvPeople.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayout.VERTICAL))
//            rvPeople.setHasFixedSize(true)
        }
    }

    override fun getData() {
        logD("get data")
        showLoading()
        movieViewModel.getMovieCast()
    }

    override fun setupDataObserver() {
        movieViewModel.movieCastData?.observe(viewLifecycleOwner) { person ->
            logD("MovieCastData: $person")
            hideLoading()
            peopleAdapter.submitData(lifecycle, person)
        }
    }

    override fun clearData() {
        super.clearData()
        binding.rvPeople.adapter = null
    }
}