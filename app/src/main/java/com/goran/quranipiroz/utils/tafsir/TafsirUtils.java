package com.goran.quranipiroz.utils.tafsir;

import android.annotation.SuppressLint;
import androidx.annotation.Nullable;
import com.goran.quranipiroz.api.models.tafsir.TafsirInfoModel;
import com.goran.quranipiroz.utils.Log;
import com.goran.quranipiroz.utils.app.AppUtils;
import com.goran.quranipiroz.utils.reader.tafsir.TafsirManager;
import com.goran.quranipiroz.utils.univ.FileUtils;
import java.util.List;
import java.util.Map;

public class TafsirUtils {
    public static final String DIR_NAME = FileUtils.createPath(AppUtils.BASE_APP_DOWNLOADED_SAVED_DATA_DIR, "tafsirs");

    public static final String AVAILABLE_TAFSIRS_FILENAME = "available_tafsirs.json";
    public static final String TAFSIR_SINGLE_FILE_NAME_FORMAT = "tafsir_verse_%d.txt";
    public static final String KEY_TAFSIR = "key.tafsir";

    public static final String URL_TAFSIR = "https://api.quran.com/api/qdc/tafsirs/%s/by_ayah/%s";

    @Nullable
    public static String getTafsirName(String key) {
        if (key == null) {
            return null;
        }

        TafsirInfoModel model = TafsirManager.getModel(key);
        if (model == null) {
            return null;
        }

        return model.getName();
    }

    public static String getTafsirSlugFromKey(String key) {
        TafsirInfoModel model = TafsirManager.getModel(key);
        if (model == null) {
            return null;
        }

        return model.getSlug();
    }

    public static String getDefaultTafsirKey() {
        Map<String, List<TafsirInfoModel>> models = TafsirManager.getModels();
        if (models == null) {
            return null;
        }

        List<TafsirInfoModel> tafsirs = models.get("en");

        if (tafsirs == null || tafsirs.isEmpty()) {
            return null;
        }

        return tafsirs.get(0).getKey();
    }

    public static boolean isKurdish(String key) {
        TafsirInfoModel model = TafsirManager.getModel(key);
        if (model == null) {
            return false;
        }

        Log.d(model.getLangCode().equals("ku"));

        return model.getLangCode().equals("ku");
    }

    public static String prepareTafsirUrlSingleVerse(String tafsirSlug, int chapterNo, int verseNo) {
        return String.format(URL_TAFSIR, tafsirSlug, chapterNo + ":" + verseNo);
    }

    @SuppressLint("DefaultLocale")
    public static String prepareTafsirFilePathSingleVerse(String tafsirKey, int chapterNo, int verseNo) {
        final String fileName = String.format(TAFSIR_SINGLE_FILE_NAME_FORMAT, verseNo);
        return FileUtils.createPath(String.valueOf(chapterNo), tafsirKey, fileName);
    }
}