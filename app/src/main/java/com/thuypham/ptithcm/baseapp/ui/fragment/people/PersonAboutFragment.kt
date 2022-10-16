package com.thuypham.ptithcm.baseapp.ui.fragment.people

import android.view.MotionEvent
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.base.BaseFragment
import com.thuypham.ptithcm.baseapp.databinding.FragmentPersonAboutBinding
import com.thuypham.ptithcm.baseapp.ui.adapter.MovieHorizontalAdapter
import com.thuypham.ptithcm.baseapp.ui.adapter.PersonDetailAdapter
import com.thuypham.ptithcm.baseapp.util.navigateToImageList
import com.thuypham.ptithcm.baseapp.util.navigateToMovieDetail
import com.thuypham.ptithcm.baseapp.util.showImageDetailDialog
import com.thuypham.ptithcm.baseapp.viewmodel.PersonViewModel
import com.thuypham.ptithcm.baselib.base.base.BaseViewAdapter
import com.thuypham.ptithcm.baselib.base.extension.*
import com.thuypham.ptithcm.data.remote.response.*
import org.koin.androidx.navigation.koinNavGraphViewModel

class PersonAboutFragment : BaseFragment<FragmentPersonAboutBinding>(R.layout.fragment_person_about) {

    private val personViewModel: PersonViewModel by koinNavGraphViewModel(R.id.person_graph)

    private val personAdapter by lazy { PersonDetailAdapter() }
    private val knowAsAdapter: BaseViewAdapter<String> by lazy { personAdapter.setupKnowAsAdapter() }
    private val imageAdapter: BaseViewAdapter<ImageInfo> by lazy { personAdapter.setupPersonImageAdapter(glide, ::onImageItemClick) }
    private val movieKnowForAdapter: BaseViewAdapter<Movie> by lazy { MovieHorizontalAdapter().setupKnowForAdapter(glide, ::onMovieClick) }

    private var isCollapse = true
    private lateinit var knowForMovie: List<Movie>

    override fun setupView() {
        setupEventClick()
        setupRecyclerView()
    }

    private fun onImageItemClick(imagePath: String) {
        showImageDetailDialog(imagePath)
    }

    private fun onMovieClick(movie: Movie) {
        navigateToMovieDetail(movie)
    }


    private fun setupRecyclerView() {
        binding.run {
            rvKnowAs.adapter = knowAsAdapter
            rvImages.apply {
                adapter = imageAdapter
                setHasFixedSize(true)
//                addOnItemTouchListener(recyclerviewScrollListener)
            }
            rvMoviesKnowFor.apply {
                adapter = movieKnowForAdapter
                setHasFixedSize(true)
//                addOnItemTouchListener(recyclerviewScrollListener)
            }
        }
    }

    private val recyclerviewScrollListener = object : RecyclerView.OnItemTouchListener {
        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            val action = e.action
            return if (binding.rvImages.canScrollHorizontally(RecyclerView.FOCUS_LEFT)
                || binding.rvImages.canScrollHorizontally(RecyclerView.FOCUS_RIGHT)
            ) {
                when (action) {
                    MotionEvent.ACTION_MOVE -> rv.parent.requestDisallowInterceptTouchEvent(true)
                }
                false
            } else {
                when (action) {
                    MotionEvent.ACTION_MOVE -> rv.parent.requestDisallowInterceptTouchEvent(false)
                }
//                binding.rvImages.removeOnItemTouchListener(this)
                false
            }
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
    }


    private fun setupEventClick() {
        binding.run {
            tvBtnShowMore.setOnClickListener(biographyClickListener)
            tvBiography.setOnClickListener(biographyClickListener)
            tvImage.setOnSingleClickListener {
                navigateToImageList(personViewModel.getPersonImagePath())
            }
        }
    }

    private val biographyClickListener = View.OnClickListener {
        isCollapse = !isCollapse
        binding.run {
            if (isCollapse) {
                tvBiography.maxLines = 3
                tvBtnShowMore.text = getString(R.string.show_more)
            } else {
                tvBiography.maxLines = tvBiography.text.length
                tvBtnShowMore.text = getString(R.string.show_less)
            }
        }
    }

    override fun setupDataObserver() {
        personViewModel.personDetail.observe(viewLifecycleOwner) { personDetail ->
            personDetail?.let { setPersonDetail(it) }
        }
        personViewModel.personInfo.observe(viewLifecycleOwner) { personInfo ->
            personInfo?.let { setPersonInfo(it) }
        }
        personViewModel.personImages.observe(viewLifecycleOwner) { personImages ->
            setupPersonImage(personImages)
        }

    }

    private fun setupPersonImage(personImages: PersonImage?) {
        if (personImages?.profiles != null) {
            binding.tvImage.show()
            binding.rvImages.show()
            imageAdapter.submitList(personImages.profiles)
        }
    }

    private fun setPersonDetail(personDetail: PersonDetail) {
        binding.run {
            val bornAsMillisecond = personDetail.birthday?.toMillisecond()
            val birthday = bornAsMillisecond?.milliSecondToDateFormat()
            val age = bornAsMillisecond?.getAge()
            val info = getString(
                R.string.person_info, age,
                birthday, personDetail.placeOfBirth, personDetail.popularity.toString()
            )
            tvInfo.text = HtmlCompat.fromHtml(info, HtmlCompat.FROM_HTML_MODE_LEGACY)
            tvBiography.text = personDetail.biography

            knowAsAdapter.submitList(personDetail.alsoKnownAs)
        }
    }

    private fun setPersonInfo(personInfo: Person) {
        binding.run {
            if (personInfo.knownFor.isNullOrEmpty()) {
                tvKnowFor.gone()
                rvMoviesKnowFor.gone()
            } else {
                knowForMovie = personInfo.knownFor!!
                tvKnowFor.show()
                rvMoviesKnowFor.show()
                movieKnowForAdapter.submitList(personInfo.knownFor)
            }
        }
    }


    override fun clearData() {
        super.clearData()
        binding.rvMoviesKnowFor.adapter = null
        binding.rvImages.adapter = null
        binding.rvKnowAs.adapter = null
    }


}