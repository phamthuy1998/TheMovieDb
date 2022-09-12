package com.thuypham.ptithcm.baseapp.ui.fragment.people

import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.ui.fragment.movie.MovieListBaseFragment
import com.thuypham.ptithcm.baseapp.viewmodel.PersonViewModel
import org.koin.androidx.navigation.koinNavGraphViewModel

class PersonMovieFragment : MovieListBaseFragment() {

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