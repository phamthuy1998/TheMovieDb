package com.thuypham.ptithcm.baseapp.util

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.ui.dialog.ImageDetailDialogFragment
import com.thuypham.ptithcm.baseapp.ui.fragment.movie.MovieCategoryFragment
import com.thuypham.ptithcm.baseapp.ui.fragment.movie.detail.MovieDetailFragment
import com.thuypham.ptithcm.baselib.base.extension.navigateTo
import com.thuypham.ptithcm.data.remote.response.Movie
import com.thuypham.ptithcm.data.remote.response.Person

object NavConstant {
    const val MOVIE_ID = "MOVIE_ID"
    const val MOVIE = "MOVIE"
    const val PERSON_INFO = "PERSON_INFO"
    const val TITLE = "TITLE"
    const val IMAGES = "IMAGES"
    const val IMAGE_PATH = "IMAGE_PATH"
}

fun Fragment.navigateToMovieDetail(movieId: Int) {
//    navigateTo(R.id.movieDatailFragment, bundle = bundleOf(NavConstant.MOVIE_ID to movieId))
    requireActivity().supportFragmentManager.beginTransaction()
        .replace(R.id.mainNavHostFragment, MovieDetailFragment().apply {
            arguments = bundleOf(NavConstant.MOVIE_ID to movieId)
        })
        .commit()
}

fun Fragment.navigateToMovieDetail(movie: Movie) {
    // Todo: update this condition to start fragment
    if (false) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.mainNavHostFragment, MovieDetailFragment().apply {
                arguments = bundleOf(NavConstant.MOVIE_ID to movie.id)
            })
            .commit()
    } else {
        navigateTo(R.id.movieDatailFragment, bundle = bundleOf(NavConstant.MOVIE_ID to movie, NavConstant.MOVIE_ID to movie.id))

    }
}

fun Fragment.navigateToMovieList(categoryType: String? = null, title: String? = null) {
    navigateTo(
        R.id.movieCategoryFragment,
        bundleOf(
            MovieCategoryFragment.CATEGORY_TYPE to categoryType,
            MovieCategoryFragment.TITLE to title,
        )
    )
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
    navigateTo(R.id.person_graph, bundle = bundleOf(NavConstant.PERSON_INFO to person))
}

fun Fragment.navigateToPeople(title: String) {
    navigateTo(R.id.peopleFragment, bundle = bundleOf(NavConstant.TITLE to title))
}

fun Fragment.navigateToImageList(images: List<String>) {
    navigateTo(R.id.imagesFragment, bundle = bundleOf(NavConstant.IMAGES to images))
}

fun Fragment.showImageDetailDialog(imagePath: String) {
    val dialog = ImageDetailDialogFragment().apply {
        arguments = bundleOf(NavConstant.IMAGE_PATH to imagePath)
    }
    dialog.show(parentFragmentManager, ImageDetailDialogFragment.TAG)
}
