package com.goran.quran.karim.dang.activities.reference

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.goran.quran.karim.dang.R
import com.goran.quran.karim.dang.adapters.reference.ADPDua
import com.goran.quran.karim.dang.components.quran.ExclusiveVerse
import com.goran.quran.karim.dang.components.quran.QuranDua
import com.goran.quran.karim.dang.components.quran.QuranMeta
import com.goran.quran.karim.dang.databinding.ActivityExclusiveVersesBinding

class ActivityDua : ActivityExclusiveVersesBase() {
    override fun onQuranMetaReady(
        activityView: View,
        intent: Intent,
        savedInstanceState: Bundle?,
        quranMeta: QuranMeta
    ) {
        QuranDua.prepareInstance(this, quranMeta) { references ->
            initContent(ActivityExclusiveVersesBinding.bind(activityView), references, R.string.strTitleFeaturedDuas)
        }
    }

    override fun getAdapter(
        context: Context,
        width: Int,
        exclusiveVerses: List<ExclusiveVerse>
    ): RecyclerView.Adapter<*> {
        return ADPDua(context, width, exclusiveVerses)
    }
}