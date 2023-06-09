package com.goran.quranipiroz.utils.app

import com.goran.quranipiroz.utils.univ.FileUtils

object AppUtils {
    @JvmField
    val BASE_APP_DOWNLOADED_SAVED_DATA_DIR: String = FileUtils.createPath(
        "downloaded",
        "saved_data"
    )!!

    @JvmField
    val APP_OTHER_DIR: String = FileUtils.createPath(BASE_APP_DOWNLOADED_SAVED_DATA_DIR, "other")!!
}
