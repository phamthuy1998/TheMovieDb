package com.thuypham.ptithcm.baseapp.ui.fragment

import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.viewmodel.PersonViewModel
import org.koin.androidx.navigation.koinNavGraphViewModel

class PersonMovieFragment : PersonBaseFragment() {

    private val personViewModel: PersonViewModel by koinNavGraphViewModel(R.id.person_graph)

    override fun getData() {
        personViewModel.getMovieList()
    }

    override fun setupDataObserver() {
        personViewModel.movieResponse.observe(viewLifecycleOwner){
            submitRecyclerViewData(it)
        }
    }

}