/*
 * Created by Faisal Khan on (c) 16/8/2021.
 */
package com.goran.quran.karim.dang.components.tafsir

import com.goran.quran.karim.dang.api.models.tafsir.TafsirInfoModel

class TafsirGroupModel(
    val langCode: String,
) {
    var langName = ""
    var tafsirs: List<TafsirInfoModel> = ArrayList()
    var isExpanded = false
}
