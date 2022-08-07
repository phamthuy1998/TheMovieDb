package com.thuypham.ptithcm.baseapp.ui.fragment

import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.base.BaseFragment
import com.thuypham.ptithcm.baseapp.databinding.FragmentPersonDetailBinding
import com.thuypham.ptithcm.baseapp.extension.loadImage
import com.thuypham.ptithcm.baseapp.ui.adapter.PersonPagerAdapter
import com.thuypham.ptithcm.baseapp.util.NavConstant
import com.thuypham.ptithcm.baseapp.util.showImageDetailDialog
import com.thuypham.ptithcm.baseapp.viewmodel.PersonViewModel
import com.thuypham.ptithcm.baselib.base.extension.goBack
import com.thuypham.ptithcm.baselib.base.extension.setOnSingleClickListener
import com.thuypham.ptithcm.data.remote.response.Person
import org.koin.androidx.navigation.koinNavGraphViewModel
import kotlin.math.abs

class PersonDetailFragment : BaseFragment<FragmentPersonDetailBinding>(R.layout.fragment_person_detail) {
    companion object {
        const val SWITCH_BOUND = 0.9f
        const val TO_EXPANDED = 0
        const val TO_COLLAPSED = 1
        const val WAIT_FOR_SWITCH = 0
        const val SWITCHED = 1
    }

    private val personViewModel: PersonViewModel by koinNavGraphViewModel(R.id.person_graph)

    private var personInfo: Person? = null

    // Collapse toolbar variable
    private var avatarAnimateStartPointY: Float = 0F
    private var avatarCollapseAnimationChangeWeight: Float = 0F
    private var isCalculated = false
    private var verticalToolbarAvatarMargin = 0F
    private var expandAvatarSize: Float = 0F
    private var collapseImageSize: Float = 0F
    private var statusBarHeight = 0
    private var horizontalToolbarAvatarMargin: Float = 0F
    private var cashCollapseState: Pair<Int, Int>? = null
    private lateinit var pagerAdapter: PersonPagerAdapter

    private val tabTitles by lazy {
        arrayListOf(
            getString(R.string.person_tab_layout_about),
            getString(R.string.person_tab_layout_movies),
            getString(R.string.person_tab_layout_tv_shows)
        )
    }

    override fun setupFirst() {
        arguments?.let {
            personInfo = it.getParcelable(NavConstant.PERSON_INFO)
            personViewModel.setPersonInfo(personInfo)
        }
    }

    override fun getData() {
        val personId = personInfo?.id
        if (personId != null) {
            personViewModel.setPersonId(personId)
            personViewModel.getPersonDetail()
            personViewModel.getPersonImage()
        } else {
            showSnackBar("Can't get data")
        }
    }


    override fun setupView() {
        personInfo?.let { setPersonInfo(it) }
        setupAppbarCollapse()
        setupViewPagerWithTabLayout()
    }

    private fun setupViewPagerWithTabLayout() {
        binding.run {
            pagerAdapter = PersonPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)
            viewPager.adapter = pagerAdapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabTitles[position]
            }.attach()
        }
    }

    private fun setupAppbarCollapse() {
        expandAvatarSize = resources.getDimension(R.dimen.expandAvtSize)
        collapseImageSize = resources.getDimension(R.dimen.collapseImageSize)
        horizontalToolbarAvatarMargin = resources.getDimension(R.dimen.margin24)
        (binding.toolbar.height - collapseImageSize) * 2
        val statusBarHeightId = resources.getIdentifier("status_bar_height", "dimen", "android")
        statusBarHeight = resources.getDimensionPixelSize(statusBarHeightId)
        binding.appbar.addOnOffsetChangedListener(appbarOffsetChangeListener)
    }

    private val appbarOffsetChangeListener by lazy {
        AppBarLayout.OnOffsetChangedListener { appBarLayout, i ->
            if (isCalculated.not()) {
                avatarAnimateStartPointY = abs((appBarLayout.height - statusBarHeight - (expandAvatarSize + horizontalToolbarAvatarMargin)) / appBarLayout.totalScrollRange)
                avatarCollapseAnimationChangeWeight = 1 / (1 - avatarAnimateStartPointY)
                verticalToolbarAvatarMargin = (binding.toolbar.height - collapseImageSize) * 2
                isCalculated = true
            }
            updateCollapseLayout(abs(i / appBarLayout.totalScrollRange.toFloat()))
        }
    }

    private fun setPersonInfo(person: Person) {
        binding.run {
            tvPersonName.text = person.name
            tvTitleToolbar.text = person.name
            tvKnowFor.text = person.knownForDepartment
            loadImage(ivAvatar, person.profilePath)
            ivAvatar.setOnSingleClickListener {
                person.profilePath?.let { it1 -> showImageDetailDialog(it1) }
            }

            val backDropPath = person.knownFor?.first()?.backdropPath
            loadImage(ivCover, backDropPath, false)
        }
    }

    private fun updateCollapseLayout(offset: Float) {
        /* apply levels changes*/
        when (offset) {
            in 0.15F..1F -> {
                binding.tvPersonName.apply {
                    if (visibility != View.VISIBLE) visibility = View.VISIBLE
                    alpha = (1 - offset) * 0.35F
                }
            }

            in 0F..0.15F -> {
                binding.tvPersonName.alpha = (1f)
                binding.ivAvatar.alpha = 1f
            }
        }

        /** collapse - expand switch*/
        when {
            offset < SWITCH_BOUND -> Pair(TO_EXPANDED, cashCollapseState?.second ?: WAIT_FOR_SWITCH)
            else -> Pair(TO_COLLAPSED, cashCollapseState?.second ?: WAIT_FOR_SWITCH)
        }.apply {
            when {
                cashCollapseState != null && cashCollapseState != this -> {
                    when (first) {
                        TO_EXPANDED -> {
                            /* set avatar on start position (center of parent frame layout)*/
                            binding.ivAvatar.translationX = 0F
                            /**/
                            /* hide top titles on toolbar*/
                            binding.tvTitleToolbar.visibility = View.INVISIBLE
                            binding.backgroundToolbar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.transparent))
                        }
                        TO_COLLAPSED -> {
                            binding.backgroundToolbar.apply {
                                alpha = 0F
                                setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.bg_toolbar))
                                animate().setDuration(250).alpha(1.0F)
                            }
                            /* show titles on toolbar with animation*/
                            binding.tvTitleToolbar.apply {
                                visibility = View.VISIBLE
                                alpha = 0F
                                animate().setDuration(500).alpha(1.0f)
                            }
                        }
                    }
                    cashCollapseState = Pair(first, SWITCHED)
                }
                else -> {
                    cashCollapseState = Pair(first, WAIT_FOR_SWITCH)
                }
            }

            /* Collapse avatar img*/
            binding.ivAvatar.apply {
                when {
                    offset > avatarAnimateStartPointY -> {
                        val avatarCollapseAnimateOffset = (offset - avatarAnimateStartPointY) * avatarCollapseAnimationChangeWeight
                        val avatarSize = expandAvatarSize - (expandAvatarSize - collapseImageSize) * avatarCollapseAnimateOffset
                        this.layoutParams.also {
                            it.height = Math.round(avatarSize)
                            it.width = Math.round(avatarSize)
                        }
                        binding.tvTemp.setTextSize(TypedValue.COMPLEX_UNIT_PX, offset)

                        this.translationX = ((binding.appbar.width - horizontalToolbarAvatarMargin - avatarSize) / 2) * avatarCollapseAnimateOffset
                        this.translationY = ((binding.toolbar.height - verticalToolbarAvatarMargin - avatarSize) / 2) * avatarCollapseAnimateOffset
                    }
                    else -> this.layoutParams.also {
                        if (it.height != expandAvatarSize.toInt()) {
                            it.height = expandAvatarSize.toInt()
                            it.width = expandAvatarSize.toInt()
                            this.layoutParams = it
                        }
                        translationX = 0f
                    }
                }
            }
        }
    }


    override fun setupToolbar() {
        super.setupToolbar()
        binding.icBack.setOnSingleClickListener {
            goBack()
        }
    }

    override fun setupDataObserver() {
        personViewModel.personDetail.observe(viewLifecycleOwner) {

        }
    }

    override fun clearData() {
        super.clearData()
        binding.appbar.removeOnOffsetChangedListener(appbarOffsetChangeListener)
        binding.appbar.removeView(binding.toolbar)
    }
}