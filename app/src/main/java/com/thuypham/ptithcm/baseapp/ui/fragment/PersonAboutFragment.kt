package com.thuypham.ptithcm.baseapp.ui.fragment

import androidx.core.text.HtmlCompat
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.base.BaseFragment
import com.thuypham.ptithcm.baseapp.databinding.FragmentPersonAboutBinding
import com.thuypham.ptithcm.baseapp.ui.adapter.PersonDetailAdapter
import com.thuypham.ptithcm.baseapp.viewmodel.PersonViewModel
import com.thuypham.ptithcm.baselib.base.base.BaseViewAdapter
import com.thuypham.ptithcm.baselib.base.extension.*
import com.thuypham.ptithcm.data.remote.response.Person
import com.thuypham.ptithcm.data.remote.response.PersonDetail
import com.thuypham.ptithcm.data.remote.response.PersonImage
import com.thuypham.ptithcm.data.remote.response.Profile
import org.koin.androidx.navigation.koinNavGraphViewModel

class PersonAboutFragment : BaseFragment<FragmentPersonAboutBinding>(R.layout.fragment_person_about) {

    private val personViewModel: PersonViewModel by koinNavGraphViewModel(R.id.person_graph)

    private val personAdapter by lazy { PersonDetailAdapter() }
    private val knowAsAdapter: BaseViewAdapter<String> by lazy { personAdapter.setupKnowAsAdapter() }
    private val imageAdapter: BaseViewAdapter<Profile> by lazy { personAdapter.setupPersonImageAdapter() }

    private var isCollapse = true

    override fun setupView() {
        setupEventClick()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.run {
            rvKnowAs.adapter = knowAsAdapter

            rvImages.adapter = imageAdapter
            rvImages.setHasFixedSize(true)
        }
    }

    private fun setupEventClick() {
        binding.run {
            tvBtnShowMore.setOnSingleClickListener {
                collapseOrExpandBiography()
            }
            tvBiography.setOnSingleClickListener {
                collapseOrExpandBiography()
            }
            layoutImages.setOnSingleClickListener {
                // open Image list
            }
        }
    }

    private fun collapseOrExpandBiography() {
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
            binding.layoutImages.show()
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

        }
    }


}