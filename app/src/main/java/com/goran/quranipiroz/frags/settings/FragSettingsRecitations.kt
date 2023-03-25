package com.goran.quranipiroz.frags.settings

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.goran.quranipiroz.R
import com.goran.quranipiroz.activities.ActivityReader
import com.goran.quranipiroz.activities.readerSettings.ActivitySettings
import com.goran.quranipiroz.adapters.recitation.ADPRecitations
import com.goran.quranipiroz.components.recitation.RecitationModel
import com.goran.quranipiroz.databinding.FragSettingsTranslBinding
import com.goran.quranipiroz.utils.app.RecitationManager
import com.goran.quranipiroz.utils.receivers.NetworkStateReceiver
import com.goran.quranipiroz.utils.sharedPrefs.SPAppActions
import com.goran.quranipiroz.utils.sharedPrefs.SPReader
import com.goran.quranipiroz.utils.univ.FileUtils
import com.goran.quranipiroz.utils.univ.StringUtils
import com.goran.quranipiroz.views.BoldHeader
import com.goran.quranipiroz.views.BoldHeader.BoldHeaderCallback
import com.goran.quranipiroz.widgets.PageAlert
import java.util.regex.Pattern

class FragSettingsRecitations : FragSettingsBase() {

    private lateinit var mBinding: FragSettingsTranslBinding
    private lateinit var mFileUtils: FileUtils

    private val mAdapter = ADPRecitations()
    private var mModels: List<RecitationModel>? = null
    private var mPageAlert: PageAlert? = null

    private var mInitialRecitation: String? = null

    override fun getFragTitle(ctx: Context): String = ctx.getString(R.string.strTitleSelectReciter)

    override val layoutResource = R.layout.frag_settings_transl

    override fun getFinishingResult(ctx: Context): Bundle? {
        if (SPReader.getSavedRecitationSlug(ctx) != mInitialRecitation) {
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

                override fun onSearchRequest(searchBox: EditText, newText: CharSequence) {
                    search(newText)
                }
            })

            disableRightBtn(false)
            setSearchHint(R.string.strHintSearchReciter)
            setRightIconRes(
                R.drawable.dr_icon_refresh,
                activity.getString(R.string.strLabelRefresh)
            )
        }
    }

    override fun onViewReady(ctx: Context, view: View, savedInstanceState: Bundle?) {
        mFileUtils = FileUtils.newInstance(ctx)
        mInitialRecitation = SPReader.getSavedRecitationSlug(ctx)
        mBinding = FragSettingsTranslBinding.bind(view)

        init(ctx)
    }

    private fun init(ctx: Context) {
        refresh(ctx, SPAppActions.getFetchRecitationsForce(ctx))
    }

    private fun initPageAlert(ctx: Context) {
        mPageAlert = PageAlert(ctx)
    }

    private fun refresh(ctx: Context, force: Boolean) {
        if (force && !NetworkStateReceiver.isNetworkConnected(ctx)) {
            noInternet(ctx)
        }

        showLoader()
        RecitationManager.prepare(ctx, force) {
            val models = RecitationManager.getModels()

            if (!models.isNullOrEmpty()) {
                populateTranslations(ctx, models)
            } else {
                noRecitersAvailable(ctx)
            }

            hideLoader()
        }
    }

    private fun search(query: CharSequence) {
        val models = mModels ?: return

        if (query.isEmpty()) {
            if (mAdapter.itemCount != models.size) {
                resetAdapter(models)
            }
            return
        }
        val pattern = Pattern.compile(
            StringUtils.escapeRegex(query.toString()),
            Pattern.CASE_INSENSITIVE or Pattern.DOTALL
        )

        val found = ArrayList<RecitationModel>()
        for (model in models) {
            if (pattern.matcher(model.reciter).find() || pattern.matcher(model.getReciterName()).find()) {
                found.add(model)
            }
        }

        resetAdapter(found)
    }

    private fun populateTranslations(ctx: Context, models: List<RecitationModel>) {
        mModels = models

        mBinding.list.layoutManager = LinearLayoutManager(ctx)
        (mBinding.list.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false

        resetAdapter(models)

        activity()?.header?.apply {
            setShowSearchIcon(true)
            setShowRightIcon(true)
        }
    }

    private fun resetAdapter(models: List<RecitationModel>) {
        mAdapter.setModels(models)
        mBinding.list.adapter = mAdapter
    }

    private fun noRecitersAvailable(ctx: Context) {
        showAlert(ctx, 0, R.string.strMsgRecitationsNoAvailable, R.string.strLabelRefresh) {
            refresh(ctx, true)
        }
    }

    private fun showLoader() {
        hideAlert()
        mBinding.loader.visibility = View.VISIBLE

        activity()?.header?.apply {
            setShowRightIcon(false)
            setShowSearchIcon(false)
        }
    }

    private fun hideLoader() {
        mBinding.loader.visibility = View.GONE

        activity()?.header?.apply {
            setShowRightIcon(true)
            setShowSearchIcon(true)
        }
    }

    private fun showAlert(ctx: Context, titleRes: Int, msgRes: Int, btnRes: Int, action: Runnable) {
        hideLoader()

        if (mPageAlert == null) {
            initPageAlert(ctx)
        }

        mPageAlert!!.apply {
            setIcon(null)
            setMessage(if (titleRes > 0) ctx.getString(titleRes) else "", ctx.getString(msgRes))
            setActionButton(btnRes, action)
            show(mBinding.container)
        }

        activity()?.header?.apply {
            setShowSearchIcon(false)
            setShowRightIcon(true)
        }
    }

    private fun hideAlert() {
        mPageAlert?.remove()
    }

    private fun noInternet(ctx: Context) {
        if (mPageAlert == null) {
            initPageAlert(ctx)
        }

        mPageAlert!!.apply {
            setupForNoInternet { refresh(ctx, true) }
            show(mBinding.container)
        }

        activity()?.header?.apply {
            setShowSearchIcon(false)
            setShowRightIcon(true)
        }
    }
}
