package com.goran.quran.karim.dang.views.homepage2

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import com.peacedesign.android.utils.Dimen
import com.goran.quran.karim.dang.R
import com.goran.quran.karim.dang.activities.reference.ActivitySolutionVerses
import com.goran.quran.karim.dang.adapters.reference.ADPSolutionVerses
import com.goran.quran.karim.dang.components.quran.ExclusiveVerse
import com.goran.quran.karim.dang.components.quran.QuranMeta
import com.goran.quran.karim.dang.components.quran.SituationVerse
import com.goran.quran.karim.dang.databinding.LytHomepageTitledItemTitleBinding
import com.goran.quran.karim.dang.utils.extensions.color

class QuranSolutionVersesLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : HomepageCollectionLayoutBase(context, attrs, defStyleAttr) {
    override fun getHeaderTitle(): Int {
        return R.string.titleSolutionVerses
    }

    override fun getHeaderIcon(): Int {
        return R.drawable.dr_icon_read_quran
    }

    override fun showViewAllBtn(): Boolean {
        return true
    }

    override fun setupHeader(header: LytHomepageTitledItemTitleBinding) {
        header.titleIcon.setColorFilter(context.color(R.color.warning))
    }

    override fun onViewAllClick() {
        context.startActivity(Intent(context, ActivitySolutionVerses::class.java))
    }

    private fun refreshVerses(ctx: Context, verses: List<ExclusiveVerse>) {
        hideLoader()

        val featured = verses.subList(0, verses.size.coerceAtMost(10))
        resolveListView().adapter = ADPSolutionVerses(ctx, Dimen.dp2px(ctx, 200f), featured)
    }

    override fun refresh(quranMeta: QuranMeta) {
        showLoader()

        SituationVerse.prepareInstance(context, quranMeta) { references ->
            refreshVerses(context, references)
        }
    }
}