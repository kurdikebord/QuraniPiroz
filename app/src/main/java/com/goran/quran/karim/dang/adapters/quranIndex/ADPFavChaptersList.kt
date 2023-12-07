package com.goran.quran.karim.dang.adapters.quranIndex

import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.peacedesign.android.widget.dialog.base.PeaceDialog
import com.goran.quran.karim.dang.R
import com.goran.quran.karim.dang.frags.readerindex.FragReaderIndexFavChapters
import com.goran.quran.karim.dang.utils.extensions.dp2px
import com.goran.quran.karim.dang.utils.extensions.updateMargins
import com.goran.quran.karim.dang.utils.reader.factory.ReaderFactory.startChapter
import com.goran.quran.karim.dang.widgets.chapterCard.ChapterCard

class ADPFavChaptersList(
    private val fragment: FragReaderIndexFavChapters,
) : RecyclerView.Adapter<ADPFavChaptersList.VHChapter>() {
    var chapterNos: List<Int> = ArrayList()

    init {
        setHasStableIds(true)
    }

    override fun getItemCount() = chapterNos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHChapter {
        val chapterCard = ChapterCard(parent.context, true).apply {
            setBackgroundResource(R.drawable.dr_bg_chapter_card)
            elevation = parent.context.dp2px(2f).toFloat()
        }

        val params = chapterCard.layoutParams

        if (params is MarginLayoutParams) {
            params.updateMargins(parent.context.dp2px(5f))
        }

        return VHChapter(chapterCard)
    }

    override fun onBindViewHolder(holder: VHChapter, position: Int) {
        holder.bind(chapterNos[position])
    }

    inner class VHChapter(private val chapterCard: ChapterCard) : RecyclerView.ViewHolder(chapterCard) {
        fun bind(chapterNo: Int) {
            val chapterName = fragment.quranMeta.getChapterName(itemView.context, chapterNo)
            val chapterNameTrans = fragment.quranMeta.getChapterNameTranslation(chapterNo)

            chapterCard.let {
                it.chapterNumber = chapterNo
                it.setName(chapterName, chapterNameTrans)
                it.setOnClickListener { v -> startChapter(v.context, chapterNo) }
                it.onFavoriteButtonClickListener = Runnable {
                    promptAddToFavourites(chapterNo, chapterName, chapterNameTrans)
                }
            }
        }

        private fun promptAddToFavourites(chapterNo: Int, chapterName: String, nameTranslation: String) {
            val context = itemView.context
            val chapterCard = ChapterCard(context).apply {
                chapterNumber = chapterNo
                setName(chapterName, nameTranslation)
                setBackgroundResource(R.drawable.dr_bg_chapter_card_bordered)
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    updateMargins(itemView.context.dp2px(10f))
                }
            }

            PeaceDialog.newBuilder(context)
                .setTitle(R.string.titleRemoveFromFavourites)
                .setView(chapterCard)
                .setDialogGravity(PeaceDialog.GRAVITY_BOTTOM)
                .setNegativeButton(R.string.strLabelCancel, null)
                .setPositiveButton(R.string.strLabelRemove) { _, _ ->
                    fragment.favChaptersModel.removeFromFavourites(context, chapterNo)
                }.show()
        }
    }
}