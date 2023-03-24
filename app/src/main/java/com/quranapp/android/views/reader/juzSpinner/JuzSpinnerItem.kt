package com.goran.quranipiroz.views.reader.juzSpinner

import com.goran.quranipiroz.utils.univ.StringUtils
import com.goran.quranipiroz.views.reader.spinner.ReaderSpinnerItem

class JuzSpinnerItem(label: CharSequence) : ReaderSpinnerItem() {
    var juzNumber = -1
    var nameArabic = ""
    var nameTransliterated = ""
        set(nameTransliterated) {
            field = nameTransliterated
            searchKeyword = StringUtils.stripDiacritics(nameTransliterated)
        }

    var searchKeyword: String? = null
        private set

    init {
        super.label = label
    }
}
