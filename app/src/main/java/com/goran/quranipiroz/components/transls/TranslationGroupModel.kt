package com.goran.quranipiroz.components.transls


class TranslationGroupModel(
    val langCode: String,

) {
    var langName = ""
    var translations: ArrayList<TranslModel> = ArrayList()
    var isExpanded = false
}
