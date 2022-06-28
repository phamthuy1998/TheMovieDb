package com.thuypham.ptithcm.baseapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.thuypham.ptithcm.baseapp.ui.fragment.PersonAboutFragment
import com.thuypham.ptithcm.baseapp.ui.fragment.PersonMovieFragment
import com.thuypham.ptithcm.baseapp.ui.fragment.PersonTVShowFragment

class PersonPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    companion object {
        const val pageSize = 3
        const val POSITION = "POSITION"
    }

    override fun getItemCount(): Int {
        return pageSize
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                PersonAboutFragment()
            }
            1 -> {
                PersonMovieFragment()
            }
            else -> {
                PersonTVShowFragment()
            }
        }
    }
}