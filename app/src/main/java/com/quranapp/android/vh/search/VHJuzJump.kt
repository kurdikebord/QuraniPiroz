package com.goran.quranipiroz.vh.search

import com.goran.quranipiroz.components.search.JuzJumpModel
import com.goran.quranipiroz.components.search.SearchResultModelBase
import com.goran.quranipiroz.databinding.LytReaderJuzSpinnerItemBinding
import com.goran.quranipiroz.utils.reader.factory.ReaderFactory.startJuz

class VHJuzJump(private val mBinding: LytReaderJuzSpinnerItemBinding, applyMargins: Boolean) : VHSearchResultBase(
    mBinding.root
) {
    init {
        setupJumperView(mBinding.root, applyMargins)
    }

    override fun bind(model: SearchResultModelBase, pos: Int) {
        (model as JuzJumpModel).apply {
            mBinding.juzSerial.text = model.juzSerial
            mBinding.root.setOnClickListener { startJuz(it.context, model.juzNo) }
        }
    }
}
