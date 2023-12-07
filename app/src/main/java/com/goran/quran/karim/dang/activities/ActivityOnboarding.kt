package com.goran.quran.karim.dang.activities

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.goran.quran.karim.dang.R
import com.goran.quran.karim.dang.activities.base.BaseActivity
import com.goran.quran.karim.dang.adapters.utility.ViewPagerAdapter2
import com.goran.quran.karim.dang.databinding.ActivityOnboardBinding
import com.goran.quran.karim.dang.frags.onboard.FragOnboardLanguage
import com.goran.quran.karim.dang.frags.onboard.FragOnboardRecitation
import com.goran.quran.karim.dang.frags.onboard.FragOnboardThemes
import com.goran.quran.karim.dang.frags.onboard.FragOnboardTranslations
import com.goran.quran.karim.dang.utils.gesture.HoverPushOpacityEffect
import com.goran.quran.karim.dang.utils.sharedPrefs.SPAppActions.setRequireOnboarding
import com.goran.quran.karim.dang.utils.simplified.SimpleTabSelectorListener

class ActivityOnboarding : BaseActivity() {
    private lateinit var binding: ActivityOnboardBinding
    private lateinit var titles: Array<String>
    private lateinit var descs: Array<String>

    override fun shouldInflateAsynchronously() = true

    override fun getLayoutResource() = R.layout.activity_onboard

    private val lastPageIndex get() = titles.size - 1
    private var currentPageIndex = 0

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("currentPageIndex", currentPageIndex)
        super.onSaveInstanceState(outState)
    }

    override fun preActivityInflate(savedInstanceState: Bundle?) {
        currentPageIndex = savedInstanceState?.getInt("currentPageIndex", 0) ?: 0
        super.preActivityInflate(savedInstanceState)
    }

    override fun onActivityInflated(activityView: View, savedInstanceState: Bundle?) {
        binding = ActivityOnboardBinding.bind(activityView)

        prepare()
        navigate(currentPageIndex)

        for (button in arrayOf(binding.previous, binding.next)) {
            button.setOnTouchListener(HoverPushOpacityEffect())
        }

        binding.skip.setOnClickListener { takeOff() }
        binding.previous.setOnClickListener {
            if (currentPageIndex == 0) {
                return@setOnClickListener
            }

            navigate(--currentPageIndex)
        }
        binding.next.setOnClickListener {
            if (currentPageIndex == lastPageIndex) {
                takeOff()
                return@setOnClickListener
            }

            navigate(++currentPageIndex)
        }
    }

    private fun prepare() {
        titles = strArray(R.array.arrOnboardingTitles)
        descs = strArray(R.array.arrOnboardingDescs)

        for (title in titles) {
            binding.pagerIndicator.addTab(binding.pagerIndicator.newTab())
        }

        binding.pagerIndicator.addOnTabSelectedListener(object : SimpleTabSelectorListener() {
            override fun onTabSelected(tab: TabLayout.Tab) {
                navigate(tab.position)
            }
        })

        initViewPager(binding.board)
    }

    private fun initViewPager(viewPager: ViewPager2) {
        val adapter = ViewPagerAdapter2(this).apply {
            arrayOf(
                FragOnboardLanguage(),
                FragOnboardThemes(),
                FragOnboardTranslations(),
                FragOnboardRecitation()
            ).forEachIndexed { index, frag ->
                addFragment(frag, titles[index])
            }
        }

        viewPager.let {
            it.adapter = adapter
            it.offscreenPageLimit = adapter.itemCount
            it.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
            it.isUserInputEnabled = false
        }
    }

    private fun navigate(index: Int) {
        if (index < 0 || index > lastPageIndex) {
            return
        }

        currentPageIndex = index

        binding.let {
            it.previous.visibility = if (index == 0) View.GONE else View.VISIBLE
            it.next.setText(
                if (index == lastPageIndex) R.string.strLabelStart else R.string.strLabelNext
            )
            it.pagerIndicator.selectTab(it.pagerIndicator.getTabAt(index))

            it.title.text = titles[index]
            it.desc.text = descs[index]
            it.board.currentItem = index
        }
    }

    private fun takeOff() {
        setRequireOnboarding(this, false)

        launchMainActivity()
        finish()
    }
}
