package com.goran.quran.karim.dang.frags.settings.recitations.manage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import com.goran.quran.karim.dang.R
import com.goran.quran.karim.dang.activities.readerSettings.ActivitySettings
import com.goran.quran.karim.dang.adapters.utility.ViewPagerAdapter2
import com.goran.quran.karim.dang.databinding.FragSettingsRecitationsBinding
import com.goran.quran.karim.dang.databinding.LytReaderIndexTabBinding
import com.goran.quran.karim.dang.frags.settings.FragSettingsBase
import com.goran.quran.karim.dang.frags.settings.recitations.FragSettingsRecitationsArabic
import com.goran.quran.karim.dang.frags.settings.recitations.FragSettingsRecitationsBase
import com.goran.quran.karim.dang.frags.settings.recitations.FragSettingsRecitationsTranslation
import com.goran.quran.karim.dang.views.BoldHeader
import com.goran.quran.karim.dang.views.BoldHeader.BoldHeaderCallback

class FragSettingsManageAudio : FragSettingsBase() {
    private lateinit var binding: FragSettingsRecitationsBinding
    private var pageAdapter: ViewPagerAdapter2? = null

    override fun getFragTitle(ctx: Context): String = ctx.getString(R.string.titleManageAudio)

    override val layoutResource = R.layout.frag_settings_recitations

    override fun setupHeader(activity: ActivitySettings, header: BoldHeader) {
        super.setupHeader(activity, header)
        header.apply {
            setCallback(object : BoldHeaderCallback {
                override fun onBackIconClick() {
                    activity.onBackPressedDispatcher.onBackPressed()
                }

                override fun onRightIconClick() {
                    if (pageAdapter?.fragments == null || pageAdapter!!.fragments.isEmpty()) return
                    // refresh child fragments
                    pageAdapter!!.fragments.forEach {
                        if (it is FragSettingsRecitationsBase && !it.isRefreshing) {
                            it.refresh(activity, true)
                        }
                    }
                }

                override fun onSearchRequest(searchBox: EditText, newText: CharSequence) {
                    if (pageAdapter?.fragments == null || pageAdapter!!.fragments.isEmpty()) return

                    val currentFragment = pageAdapter!!.fragments[binding.viewPager.currentItem]
                    if (currentFragment !is FragSettingsRecitationsBase) return

                    currentFragment.search(newText)
                }
            })

            setShowSearchIcon(false)
            setShowRightIcon(false)
        }
    }

    override fun onViewReady(ctx: Context, view: View, savedInstanceState: Bundle?) {
        binding = FragSettingsRecitationsBinding.bind(view)

        init(ctx)
    }

    private fun init(ctx: Context) {
        binding.let {
            pageAdapter = ViewPagerAdapter2(requireActivity())
            it.viewPager.adapter = pageAdapter!!.apply {
                addFragment(
                    FragSettingsRecitationsArabic().apply {
                        arguments = Bundle().apply { putBoolean(FragSettingsRecitationsBase.KEY_IS_MANAGE_AUDIO, true) }
                    },
                    ctx.getString(R.string.strTitleQuran)
                )
                addFragment(
                    FragSettingsRecitationsTranslation().apply {
                        arguments = Bundle().apply { putBoolean(FragSettingsRecitationsBase.KEY_IS_MANAGE_AUDIO, true) }
                    },
                    ctx.getString(R.string.labelTranslation)
                )
            }
            it.viewPager.offscreenPageLimit = pageAdapter!!.itemCount
            it.viewPager.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
        }

        binding.tabLayout.setTabSetupCallback { viewPager, tab, position ->
            tab.customView = LytReaderIndexTabBinding.inflate(LayoutInflater.from(ctx)).apply {
                tabTitle.text = (viewPager.adapter as ViewPagerAdapter2).getPageTitle(position)
            }.root
        }

        binding.tabLayout.populateFromViewPager(binding.viewPager)
    }
}
