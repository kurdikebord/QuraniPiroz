package com.goran.quranipiroz.utils.reader;

import android.content.Context;
import android.util.Pair;
import androidx.annotation.Nullable;
import com.goran.quranipiroz.R;
import com.goran.quranipiroz.components.quran.subcomponents.QuranTranslBookInfo;
import com.goran.quranipiroz.components.transls.TranslModel;
import com.goran.quranipiroz.utils.Log;
import com.goran.quranipiroz.utils.Logger;
import com.goran.quranipiroz.utils.app.AppUtils;
import com.goran.quranipiroz.utils.sharedPrefs.SPReader;
import com.goran.quranipiroz.utils.univ.FileUtils;
import com.goran.quranipiroz.utils.univ.MessageUtils;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import kotlin.io.FilesKt;
import kotlin.text.Charsets;

public class TranslUtils {
    public static final String DIR_NAME = FileUtils.createPath(AppUtils.BASE_APP_DOWNLOADED_SAVED_DATA_DIR,
        "translations");
    public static final String DIR_NAME_4_AVAILABLE_DOWNLOADS =
        FileUtils.createPath(AppUtils.BASE_APP_DOWNLOADED_SAVED_DATA_DIR, "available_translation_downloads");
    public static final String TRANSL_INFO_FILE_NAME = "manifest.json";
    public static final String KEY_TRANSLATIONS = "key.translations";
    public static final String KEY_NEW_TRANSLATIONS = "key.translations_new";

    /**
     * Format -> translation_101_en_en_sahih-international.json
     */
    public static final String TRANSL_FILE_NAME_FORMAT = "translation_%d_%s_%s.json";
    public static final String TRANSL_AVAILABLE_DOWNLOADS_FILE_NAME = "available_downloads.json";

    public static final String TRANSL_SLUG_KU_BAMOKI = "ku_101_bamoki";

    public static final String TRANSL_SLUG_EN_SAHIH_INTERNATIONAL = "en_201_sahih-international";


    /**
     * This translation slug must be updated if it updates on the server.
     */

    public static final String TRANSL_SLUG_DEFAULT = TRANSL_SLUG_KU_BAMOKI;

    public static final String TRANSL_TRANSLITERATION = "en_transliteration";

    public static final int TRANSL_MAX_SELECTION_LIMIT = 5;

    public static Set<String> defaultTranslationSlugs() {
        Set<String> defTranslations = new HashSet<>();
        defTranslations.add(TRANSL_SLUG_DEFAULT);
        return defTranslations;
    }

    public static List<QuranTranslBookInfo> preBuiltTranslBooksInfo() {
        List<QuranTranslBookInfo> translItems = new ArrayList<>();

        String[] kuTranslations = {TRANSL_SLUG_KU_BAMOKI};
        String[] enTranslations = {TRANSL_SLUG_EN_SAHIH_INTERNATIONAL};

        for (String slug : kuTranslations) {
            translItems.add(createPrebuiltTranslBookInfo(slug, "ku", "کوردی"));
        }
        for (String slug : enTranslations) {
            translItems.add(createPrebuiltTranslBookInfo(slug, "en", "English"));
        }
        return translItems;
    }
    private static QuranTranslBookInfo createPrebuiltTranslBookInfo(String slug, String langCode, String langName) {
        QuranTranslBookInfo bookInfo = new QuranTranslBookInfo(slug);
        bookInfo.setLangCode(langCode);
        bookInfo.setLangName(getPrebuiltTranslLangName(slug));
        bookInfo.setBookName(getPrebuiltTranslBookName(slug));
        bookInfo.setAuthorName(getPrebuiltTranslAuthorName(slug));
        bookInfo.setDisplayName(getPrebuiltTranslDisplayName(slug));
        bookInfo.setLangName(langName);
        return bookInfo;
    }

    public static String getPrebuiltTranslLangName(String slug) {
        switch (slug) {
            case TRANSL_SLUG_KU_BAMOKI:
                return "کوردی";

            case TRANSL_SLUG_EN_SAHIH_INTERNATIONAL:
                return "English";

            default:
                return "";
        }
    }
    public static String getPrebuiltTranslBookName(String slug) {
        switch (slug) {
            case TRANSL_SLUG_KU_BAMOKI:
                return "پوختەی قورئان";

            case TRANSL_SLUG_EN_SAHIH_INTERNATIONAL:
                return "Sahih International";
            default:
                return "";
        }
    }
    public static String getPrebuiltTranslAuthorName(String slug) {
        switch (slug) {
            case TRANSL_SLUG_KU_BAMOKI:
                return "محمد ساڵح بامۆکی";
            case TRANSL_SLUG_EN_SAHIH_INTERNATIONAL:
                return "Saheeh International";
            default:
                return "";
        }
    }
    public static String getPrebuiltTranslDisplayName(String slug) {
        switch (slug) {

            case TRANSL_SLUG_KU_BAMOKI:
                return "پوختەی قورئان";
            case TRANSL_SLUG_EN_SAHIH_INTERNATIONAL:
                return "Sahih International";
            default:
                return "";
        }
    }
    public static String getPrebuiltTranslInfoPath(String slug) {
        switch (slug) {
            case TRANSL_SLUG_KU_BAMOKI:
                return "prebuilt_translations/ku_bamoki/manifest.json";

            case TRANSL_SLUG_EN_SAHIH_INTERNATIONAL:
                return "prebuilt_translations/en_saheeh_v1_1_1/manifest.json";
        }
        return null;
    }
    public static String getPrebuiltTranslPath(String slug) {
        switch (slug) {
            case TRANSL_SLUG_KU_BAMOKI:
                return "prebuilt_translations/ku_bamoki/ku_bamoki.json";

            case TRANSL_SLUG_EN_SAHIH_INTERNATIONAL:
                return "prebuilt_translations/en_saheeh_v1_1_1/en_saheeh_v1_1_1.json";
        }
        return null;
    }

    public static boolean isPrebuilt(String slug) {
        switch (slug) {
            case TRANSL_SLUG_KU_BAMOKI:
            case TRANSL_SLUG_EN_SAHIH_INTERNATIONAL:
                /*case TRANSL_SLUG_EN_HILALI_KHAN:*/
                return true;
        }
        return false;
    }

    public static boolean isKurdish(String slug) {
        return Objects.equals(slug.split("_")[0], "ku");
    }

    private static String prepareTranslDirPathForSpecificLangNSlug(String langCode, String translSlug) {
        return FileUtils.createPath(langCode, translSlug);
    }

    public static String prepareTranslInfoPathForSpecificLangNSlug(String langCode, String translSlug) {
        String path2TranslDir = prepareTranslDirPathForSpecificLangNSlug(langCode, translSlug);
        return FileUtils.createPath(path2TranslDir, TRANSL_INFO_FILE_NAME);
    }

    public static String prepareTranslPathForSpecificLangNSlug(int translId, String langCode, String translSlug) {
        String path2TranslDir = prepareTranslDirPathForSpecificLangNSlug(langCode, translSlug);
        String filename = String.format(TRANSL_FILE_NAME_FORMAT, translId, langCode, translSlug);
        return FileUtils.createPath(path2TranslDir, filename);
    }

    @Nullable
    public static List<Pair<QuranTranslBookInfo, File>> getTranslInfosAndFilesForMigration(FileUtils fileUtils, File translDir) throws Exception {
        File[] dirsOfLangCodes = translDir.listFiles();
        if (dirsOfLangCodes == null || dirsOfLangCodes.length == 0) {
            Log.d("Nothing was found, deleting root translation directory: " + translDir.getName());
            FilesKt.deleteRecursively(translDir);
            return null;
        }

        List<Pair<QuranTranslBookInfo, File>> translInfosAndFiles = new ArrayList<>();
        for (File langCodeDir : dirsOfLangCodes) {
            File[] translFiles = langCodeDir.listFiles();
            if (translFiles == null || translFiles.length == 0) {
                Log.d("Deleting language directory with its contents: " + langCodeDir.getName());
                FilesKt.deleteRecursively(langCodeDir);
                continue;
            }

            for (File singleTranslDir : translFiles) {
                if (!singleTranslDir.isDirectory()) {
                    FilesKt.deleteRecursively(singleTranslDir);
                    continue;
                }

                try {
                    File infoJSONFile = new File(singleTranslDir, TRANSL_INFO_FILE_NAME);
                    Pair<QuranTranslBookInfo, File> pair = readTranslInfoFromJSONFile(fileUtils, infoJSONFile);
                    if (pair == null) {
                        Logger.print(
                            "Deleting translation directory with its manifest and data files: " + singleTranslDir.getName());
                        FilesKt.deleteRecursively(singleTranslDir);
                        continue;
                    }

                    translInfosAndFiles.add(pair);
                } catch (Exception e) {
                    Logger.print(
                        "Error occurred, deleting translation directory with its manifest and data files dire: " + singleTranslDir.getName());
                    FilesKt.deleteRecursively(singleTranslDir);
                    e.printStackTrace();
                }
            }
        }
        return translInfosAndFiles;
    }

    // For migrating file base translations to database.
    private static Pair<QuranTranslBookInfo, File> readTranslInfoFromJSONFile(FileUtils fileUtils, File infoJSONFile) throws JSONException, IOException {
        if (!infoJSONFile.isFile()) {
            return null;
        }

        String json = FilesKt.readText(infoJSONFile, Charsets.UTF_8);
        JSONObject jsonObject = new JSONObject(json);

        String slug = jsonObject.optString("slug", "");

        if (isPrebuilt(slug)) return null;

        int id = jsonObject.optInt("id");
        String langCode = jsonObject.optString("lang-code", "");

        File translFile = fileUtils.getSingleTranslationFile(id, langCode, slug);
        if (!translFile.exists() || translFile.length() == 0) {
            Log.d("Translation file does not exist or is empty.");
            return null;
        }
        QuranTranslBookInfo bookInfo = new QuranTranslBookInfo(slug);
        bookInfo.setLangCode(langCode);
        bookInfo.setBookName(jsonObject.optString("book", ""));
        bookInfo.setAuthorName(jsonObject.optString("author", ""));
        bookInfo.setLangName(jsonObject.optString("lang-name", ""));
        bookInfo.setDisplayName(jsonObject.optString("display-name", ""));
        bookInfo.setLastUpdated(1636309799000L /* 2021-11-07 23:59 */);
        return new Pair<>(bookInfo, translFile);
    }

    public static boolean resolveSelectionChange(Context ctx, Set<String> slugSet, TranslModel model, boolean isSelected, boolean saveToSP) {
        String slug = model.getBookInfo().getSlug();
        if (isSelected) {
            if (slugSet.size() >= TRANSL_MAX_SELECTION_LIMIT) {
                String msg = ctx.getString(R.string.strMsgTranslSelectionLimit, TRANSL_MAX_SELECTION_LIMIT);
                String btn = ctx.getString(R.string.strLabelGotIt);
                MessageUtils.popMessage(ctx, ctx.getString(R.string.strTitleInfo), msg, btn, null);
                return false;
            }
            slugSet.add(slug);
        } else {
            slugSet.remove(slug);
        }

        if (saveToSP) {
            SPReader.setSavedTranslations(ctx, slugSet);
        }

        return true;
    }
}
