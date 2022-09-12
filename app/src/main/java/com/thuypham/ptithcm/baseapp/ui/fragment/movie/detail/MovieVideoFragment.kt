package com.thuypham.ptithcm.baseapp.ui.fragment.movie.detail

import android.widget.Toast
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment
import com.google.android.youtube.player.YouTubePlayerView
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.databinding.FragmentMovieVideoBinding
import com.thuypham.ptithcm.baselib.base.extension.setOnSingleClickListener
import com.thuypham.ptithcm.data.util.ApiHelper

class MovieVideoFragment : BaseDetailFragment<FragmentMovieVideoBinding>(R.layout.fragment_movie_video) {
    private var youtubeFragment: YouTubePlayerFragment? = null
    private var youtubePlayerView: YouTubePlayerView? = null
    lateinit var youTubePlayer: YouTubePlayer


    override fun getData() {
        super.getData()

    }

    override fun setupView() {
        super.setupView()
        binding.run {
            videoMotionLayout.transitionToEnd()
            btnClose.setOnSingleClickListener {
                closeFragment()
            }
//            youtubeFragment = childFragmentManager.findFragmentById(R.id.youtubePlayer) as? YouTubePlayerFragment
            youtubeFragment?.initialize(
                ApiHelper.youtubeKey(),
                object : YouTubePlayer.OnInitializedListener {
                    override fun onInitializationSuccess(
                        provider: YouTubePlayer.Provider,
                        youTubePlayer: YouTubePlayer, b: Boolean
                    ) {
                        youTubePlayer.fullscreenControlFlags =
                            YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION or YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE
                        this@MovieVideoFragment.youTubePlayer = youTubePlayer

                    }

                    override fun onInitializationFailure(
                        provider: YouTubePlayer.Provider,
                        youTubeInitializationResult: YouTubeInitializationResult
                    ) {
                        val requestCode = 1
                        if (youTubeInitializationResult.isUserRecoverableError) {
                            youTubeInitializationResult.getErrorDialog(requireActivity(), requestCode).show()
                        } else {
                            val errorMessage = String.format(
                                "There was an error initializing the YoutubePlayer (%1\$s)",
                                youTubeInitializationResult.toString()
                            )
                            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            )


        }
    }

    private fun closeFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .remove(this)
            .commit()
    }
}