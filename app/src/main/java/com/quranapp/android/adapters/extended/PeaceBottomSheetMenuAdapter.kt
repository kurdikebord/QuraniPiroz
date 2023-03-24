package com.goran.quranipiroz.adapters.extended

import android.content.Context
import android.view.View
import com.goran.quranipiroz.R
import com.goran.quranipiroz.utils.extensions.color
import com.goran.quranipiroz.utils.extensions.dp2px
import com.goran.quranipiroz.utils.extensions.updatePaddingVertical
import com.goran.quranipiroz.widgets.list.base.BaseListAdapter
import com.goran.quranipiroz.widgets.list.base.BaseListItem
import com.goran.quranipiroz.widgets.list.base.BaseListItemView

class PeaceBottomSheetMenuAdapter(context: Context) : BaseListAdapter(context) {
    private val mMessageColor = context.color(R.color.colorText2)

    override fun onCreateItemView(item: BaseListItem, position: Int): View {
        val view = super.onCreateItemView(item, position) as BaseListItemView

        if (item.message.isNullOrEmpty()) {
            view.containerView.updatePaddingVertical(context.dp2px(3f))
        } else {
            view.messageView?.setTextColor(mMessageColor)
        }

        return view
    }
}
