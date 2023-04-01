package com.goran.quranipiroz.adapters.tafsir

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goran.quranipiroz.R
import com.goran.quranipiroz.api.models.tafsir.TafsirInfoModel
import com.goran.quranipiroz.utils.extensions.dp2px
import com.goran.quranipiroz.utils.extensions.updatePaddings
import com.goran.quranipiroz.utils.sharedPrefs.SPReader
import com.goran.quranipiroz.widgets.compound.PeaceCompoundButton
import com.goran.quranipiroz.widgets.radio.PeaceRadioButton

class ADPTafsir(private val tafsirs: List<TafsirInfoModel>, private val selectCallback: (Int) -> Unit) :
    RecyclerView.Adapter<ADPTafsir.VHTafsirItem>() {

    inner class VHTafsirItem(private val checkBox: PeaceRadioButton) : RecyclerView.ViewHolder(checkBox) {
        fun bind(info: TafsirInfoModel) {
            checkBox.setTexts(info.name, info.author)
            checkBox.isChecked = info.isChecked
            checkBox.onCheckChangedListener = { _, _ ->
                SPReader.setSavedTafsirKey(checkBox.context, info.key)
                selectCallback(adapterPosition)
            }
        }

    }

    override fun getItemCount(): Int {
        return tafsirs.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHTafsirItem {
        return VHTafsirItem(makeCheckBox(parent.context))
    }

    private fun makeCheckBox(context: Context): PeaceRadioButton {
        return PeaceRadioButton(context).apply {
            setCompoundDirection(PeaceCompoundButton.COMPOUND_TEXT_LEFT)
            setTextAppearance(R.style.TextAppearanceCommonTitle)
            setSpaceBetween(context.dp2px(15F))

            updatePaddings(horizontalPadding = context.dp2px(15F), verticalPadding = context.dp2px(10F))

            setBackgroundResource(R.drawable.dr_bg_action)
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        }
    }

    override fun onBindViewHolder(holder: VHTafsirItem, position: Int) {
        holder.bind(tafsirs[position])
    }
}