package com.thuypham.ptithcm.baseapp.ui.adapter

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.thuypham.ptithcm.baseapp.ui.fragment.movie.detail.*
import com.thuypham.ptithcm.baseapp.ui.fragment.people.PersonTVShowFragment
import com.thuypham.ptithcm.baseapp.util.NavConstant


class MovieDetailPagerAdapter(private val movieId: Int, fragment: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragment, lifecycle) {
    companion object {
        const val pageSize = 6
    }

    override fun getItemCount(): Int {
        return pageSize
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = when (position) {
            0 -> MovieAboutFragment()
            1 -> MovieCastFragment()
            2 -> MovieCommentFragment()
            3 -> MovieReviewFragment()
            4 -> RecommendationsMovieFragment()
            5 -> SimilarMovieFragment()
            else -> {
                PersonTVShowFragment()
            }
        }
        return fragment.apply {
            arguments = bundleOf(NavConstant.MOVIE_ID to movieId)
        }
    }
}