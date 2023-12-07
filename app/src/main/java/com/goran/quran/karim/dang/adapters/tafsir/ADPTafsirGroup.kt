package com.goran.quran.karim.dang.adapters.tafsir

import android.annotation.SuppressLint
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.Gravity
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.goran.quran.karim.dang.R
import com.goran.quran.karim.dang.components.tafsir.TafsirGroupModel
import com.goran.quran.karim.dang.databinding.LytDownloadTranslationGroupItemBinding
import com.goran.quran.karim.dang.utils.extensions.*

class ADPTafsirGroup(private val models: List<TafsirGroupModel>) :
    RecyclerView.Adapter<ADPTafsirGroup.VHTafsirGroup>() {
    inner class VHTafsirGroup(private val binding: LytDownloadTranslationGroupItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.list.layoutManager = LinearLayoutManager(binding.root.context)
            (binding.list.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false

            binding.root.clipToOutline = true
        }

        @SuppressLint("RtlHardcoded")
        fun bind(group: TafsirGroupModel) {
            binding.title.let {
                it.text = if (group.isExpanded) group.langName else prepareText(group.langName, group.tafsirs.size)
                it.gravity = if (itemView.context.isRTL()) Gravity.RIGHT else Gravity.LEFT
                it.setDrawables(
                    null,
                    null,
                    itemView.context.drawable(R.drawable.dr_icon_chevron_right).rotate(
                        itemView.context,
                        if (group.isExpanded) -90f else 90f
                    ),
                    null
                )

                it.setOnClickListener {
                    group.isExpanded = !group.isExpanded
                    notifyItemChanged(bindingAdapterPosition)
                }
            }

            binding.list.visibility = (if (group.isExpanded) RecyclerView.VISIBLE else RecyclerView.GONE)

            binding.list.adapter = ADPTafsir(group.tafsirs) { changedIndex ->
                models.forEachIndexed { _, tafsirGroupModel ->
                    tafsirGroupModel.tafsirs.forEach {
                        it.isChecked = false
                    }
                }

                models[bindingAdapterPosition].tafsirs[changedIndex].isChecked = true
                notifyDataSetChanged()
            }
        }

        private fun prepareText(langName: String, size: Int): CharSequence {
            val descSS = SpannableString(
                if (size <= 1) itemView.context.getString(R.string.nItem, size)
                else itemView.context.getString(R.string.nItems, size)
            )
            descSS.setSpan(
                RelativeSizeSpan(0.8f),
                0,
                descSS.length,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            descSS.setSpan(
                ForegroundColorSpan(itemView.context.color(R.color.colorText2)),
                0,
                descSS.length,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            return TextUtils.concat(langName, "\n", descSS)
        }
    }

    override fun getItemCount(): Int {
        return models.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHTafsirGroup {
        return VHTafsirGroup(LytDownloadTranslationGroupItemBinding.inflate(parent.layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: VHTafsirGroup, position: Int) {
        holder.bind(models[position])
    }
}