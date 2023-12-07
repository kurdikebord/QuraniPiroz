package com.goran.quran.karim.dang.widgets.list.base

import android.view.View
import com.goran.quran.karim.dang.components.ComponentBase

class BaseListItem @JvmOverloads constructor(
    var icon: Int = 0,
    var label: String? = null,
    var message: String? = null
) : ComponentBase() {
    var adapter: BaseListAdapter? = null
    var itemView: View? = null
}