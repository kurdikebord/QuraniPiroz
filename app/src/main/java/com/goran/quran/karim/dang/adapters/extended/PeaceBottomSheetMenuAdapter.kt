package com.goran.quran.karim.dang.adapters.extended

import android.content.Context
import android.view.View
import com.goran.quran.karim.dang.R
import com.goran.quran.karim.dang.utils.extensions.color
import com.goran.quran.karim.dang.utils.extensions.dp2px
import com.goran.quran.karim.dang.utils.extensions.updatePaddingVertical
import com.goran.quran.karim.dang.widgets.list.base.BaseListAdapter
import com.goran.quran.karim.dang.widgets.list.base.BaseListItem
import com.goran.quran.karim.dang.widgets.list.base.BaseListItemView

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
