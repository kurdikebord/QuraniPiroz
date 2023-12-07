package com.goran.quran.karim.dang.vh.search

import com.goran.quran.karim.dang.components.search.JuzJumpModel
import com.goran.quran.karim.dang.components.search.SearchResultModelBase
import com.goran.quran.karim.dang.databinding.LytReaderJuzSpinnerItemBinding
import com.goran.quran.karim.dang.utils.reader.factory.ReaderFactory.startJuz

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
