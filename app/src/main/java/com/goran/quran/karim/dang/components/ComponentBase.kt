package com.goran.quran.karim.dang.components

import java.io.Serializable

open class ComponentBase : Serializable {
    var id = -1
    var key: String? = null
    var position = -1
    var selected = false
    var enabled = true

    @Transient
    var obj: Any? = null
}
