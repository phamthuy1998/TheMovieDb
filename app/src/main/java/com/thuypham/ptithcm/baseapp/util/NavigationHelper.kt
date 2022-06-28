package com.thuypham.ptithcm.baseapp.util

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.ui.fragment.MovieCategoryFragment
import com.thuypham.ptithcm.baseapp.ui.fragment.MovieDetailFragment
import com.thuypham.ptithcm.baseapp.ui.fragment.PeopleFragment
import com.thuypham.ptithcm.baseapp.ui.fragment.PersonDetailFragment
import com.thuypham.ptithcm.baselib.base.extension.navigateTo
import com.thuypham.ptithcm.data.remote.response.Person

fun Fragment.navigateToMovieDetail(movieId: Int) {
    navigateTo(R.id.movieDatailFragment, bundle = bundleOf(MovieDetailFragment.MOVIE_ID to movieId))
}

fun Fragment.navigateToMovieList(categoryType: String? = null, title: String? = null, genreId: Int? = null) {
    navigateTo(
        R.id.movieCategoryFragment,
        bundleOf(
            MovieCategoryFragment.CATEGORY_TYPE to categoryType,
            MovieCategoryFragment.TITLE to title,
            MovieCategoryFragment.GENRE_ID to genreId,
        )
    )
}

fun Fragment.navigateToPersonDetail(person: Person?) {
    navigateTo(R.id.person_graph, bundle = bundleOf(PersonDetailFragment.PERSON_INFO to person))
}

fun Fragment.navigateToPeople(title: String) {
    navigateTo(R.id.peopleFragment, bundle = bundleOf(PeopleFragment.TITLE to title))
}