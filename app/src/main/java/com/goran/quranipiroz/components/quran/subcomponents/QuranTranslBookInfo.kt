package com.goran.quranipiroz.components.quran.subcomponents

import com.goran.quranipiroz.utils.univ.StringUtils
import java.io.Serializable

/**
 * Holds information about a translation book.
 * e.g., information about Sahih International
 * */

class QuranTranslBookInfo(val slug: String) : Serializable {
    companion object {
        const val DISPLAY_NAME_DEFAULT_WITHOUT_HYPHEN = false
    }

    var bookName = ""
    var authorName = ""
    var displayName = ""
    var langName = ""
    var langCode = ""
    var lastUpdated: Long = -1
    var downloadPath = ""

    val isKurdish get() = langCode == "ku"

    fun getDisplayName(withoutHyphen: Boolean = DISPLAY_NAME_DEFAULT_WITHOUT_HYPHEN): String {
        return if (withoutHyphen) displayName else "${StringUtils.HYPHEN} $displayName"
    }

    fun getDisplayNameWithHyphen(): String {
        return getDisplayName(false)
    }

    override fun toString(): String {
        return "QuranTranslBookInfo(slug='$slug', langCode='$langCode'"
    }

    override fun equals(other: Any?): Boolean {
        if (other !is QuranTranslBookInfo) return false

        if (slug != other.slug) return false

        return true
    }

    override fun hashCode(): Int {
        var result = slug.hashCode()
        result = 31 * result + bookName.hashCode()
        result = 31 * result + authorName.hashCode()
        result = 31 * result + displayName.hashCode()
        result = 31 * result + langName.hashCode()
        result = 31 * result + langCode.hashCode()
        result = 31 * result + isKurdish.hashCode()
        return result
    }
}
