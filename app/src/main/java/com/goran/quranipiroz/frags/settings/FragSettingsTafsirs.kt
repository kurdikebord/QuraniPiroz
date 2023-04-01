package com.goran.quranipiroz.frags.settings

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.goran.quranipiroz.R
import com.goran.quranipiroz.activities.ActivityReader
import com.goran.quranipiroz.activities.readerSettings.ActivitySettings
import com.goran.quranipiroz.components.tafsir.TafsirModel
import com.goran.quranipiroz.databinding.FragSettingsTafsirBinding
import com.goran.quranipiroz.utils.reader.tafsir.TafsirManager
import com.goran.quranipiroz.utils.receivers.NetworkStateReceiver
import com.goran.quranipiroz.utils.sharedPrefs.SPAppActions
import com.goran.quranipiroz.utils.sharedPrefs.SPReader
import com.goran.quranipiroz.utils.univ.FileUtils
import com.goran.quranipiroz.views.BoldHeader
import com.goran.quranipiroz.views.BoldHeader.BoldHeaderCallback
import com.goran.quranipiroz.widgets.PageAlert

class FragSettingsTafsirs : FragSettingsBase() {

    private lateinit var binding: FragSettingsTafsirBinding
    private lateinit var fileUtils: FileUtils
    private lateinit var pageAlert: PageAlert

    private var initialTafsirKey: String? = null

    override fun getFragTitle(ctx: Context): String = ctx.getString(R.string.strTitleSelectTafsir)

    override val layoutResource = R.layout.frag_settings_tafsir

    override fun getFinishingResult(ctx: Context): Bundle? {
        if (SPReader.getSavedTafsirKey(ctx) != initialTafsirKey) {
            return bundleOf(ActivityReader.KEY_RECITER_CHANGED to true)
        }
        return null
    }

    override fun setupHeader(activity: ActivitySettings, header: BoldHeader) {
        super.setupHeader(activity, header)
        header.apply {
            setCallback(object : BoldHeaderCallback {
                override fun onBackIconClick() {
                    activity.onBackPressedDispatcher.onBackPressed()
                }

                override fun onRightIconClick() {
                    refresh(activity, true)
                }
            })

            disableRightBtn(false)
            setRightIconRes(
                R.drawable.dr_icon_refresh,
                activity.getString(R.string.strLabelRefresh)
            )
        }
    }

    override fun onViewReady(ctx: Context, view: View, savedInstanceState: Bundle?) {
        fileUtils = FileUtils.newInstance(ctx)
        initialTafsirKey = SPReader.getSavedRecitationSlug(ctx)
        pageAlert = PageAlert(ctx)
        binding = FragSettingsTafsirBinding.bind(view).apply {
            list.layoutManager = LinearLayoutManager(ctx)
        }

        refresh(ctx, SPAppActions.getFetchTafsirsForce(ctx))
    }

    private fun refresh(ctx: Context, force: Boolean) {
        if (force && !NetworkStateReceiver.isNetworkConnected(ctx)) {
            noInternet(ctx)
        }

        showLoader()

        TafsirManager.prepare(ctx, force) {
            val models = TafsirManager.getModels()

            if (!models.isNullOrEmpty()) {
                initChips(ctx, models)
            } else {
                noTafsirsAvailable(ctx)
            }

            hideLoader()
        }
    }

    private fun initChips(ctx: Context, models: Map<String, List<TafsirModel>>) {
        //populateTafsirs(models)
    }

    private fun populateTafsirs(models: List<TafsirModel>) {
        resetAdapter(models)

        activity()?.header?.apply {
            setShowRightIcon(true)
        }
    }

    private fun resetAdapter(models: List<TafsirModel>) {
        // binding.list.adapter = ADPTafsir(models)
    }

    private fun noTafsirsAvailable(ctx: Context) {
        showAlert(ctx, 0, R.string.strMsgTafsirsNoAvailable, R.string.strLabelRefresh) {
            refresh(ctx, true)
        }
    }

    private fun showLoader() {
        hideAlert()
        binding.loader.visibility = View.VISIBLE

        activity()?.header?.apply {
            setShowRightIcon(false)
        }
    }

    private fun hideLoader() {
        binding.loader.visibility = View.GONE

        activity()?.header?.apply {
            setShowRightIcon(true)
        }
    }

    private fun showAlert(ctx: Context, titleRes: Int, msgRes: Int, btnRes: Int, action: Runnable) {
        hideLoader()

        pageAlert.apply {
            setIcon(null)
            setMessage(if (titleRes > 0) ctx.getString(titleRes) else "", ctx.getString(msgRes))
            setActionButton(btnRes, action)
            show(binding.container)
        }

        activity()?.header?.apply {
            setShowRightIcon(true)
        }
    }

    private fun hideAlert() {
        pageAlert.remove()
    }

    private fun noInternet(ctx: Context) {
        pageAlert.apply {
            setupForNoInternet { refresh(ctx, true) }
            show(binding.container)
        }

        activity()?.header?.apply {
            setShowRightIcon(true)
        }
    }
}
