package com.goran.quranipiroz.utils.univ

import android.text.Spannable

class SpannableFactory : Spannable.Factory() {
    override fun newSpannable(source: CharSequence): Spannable {
        return if (source is Spannable) {
            source
        } else {
            super.newSpannable(source)
        }
    }
}
