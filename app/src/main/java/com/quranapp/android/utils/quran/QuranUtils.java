package com.goran.quranipiroz.utils.quran;

import com.goran.quranipiroz.components.quran.QuranMeta;

import java.util.stream.IntStream;

import kotlin.Pair;

public abstract class QuranUtils {
    public static String getBismillahUnicode() {
        return "\ufdfd";
    }

    public static String getChapterIconUnicode(int chapterNumber) {
        String unicode = null;
        switch (chapterNumber) {
            case 0: unicode = "\ue903";
                break; // Surah text
            case 1: unicode = "\ue904";
                break;
            case 2: unicode = "\ue905";
                break;
            case 3: unicode = "\ue906";
                break;
            case 4: unicode = "\ue907";
                break;
            case 5: unicode = "\ue908";
                break;
            case 6: unicode = "\ue90b";
                break;
            case 7: unicode = "\ue90c";
                break;
            case 8: unicode = "\ue90d";
                break;
            case 9: unicode = "\ue90e";
                break;
            case 10: unicode = "\ue90f";
                break;
            case 11: unicode = "\ue910";
                break;
            case 12: unicode = "\ue911";
                break;
            case 13: unicode = "\ue912";
                break;
            case 14: unicode = "\ue913";
                break;
            case 15: unicode = "\ue914";
                break;
            case 16: unicode = "\ue915";
                break;
            case 17: unicode = "\ue916";
                break;
            case 18: unicode = "\ue917";
                break;
            case 19: unicode = "\ue918";
                break;
            case 20: unicode = "\ue919";
                break;
            case 21: unicode = "\ue91a";
                break;
            case 22: unicode = "\ue91b";
                break;
            case 23: unicode = "\ue91c";
                break;
            case 24: unicode = "\ue91d";
                break;
            case 25: unicode = "\ue91e";
                break;
            case 26: unicode = "\ue91f";
                break;
            case 27: unicode = "\ue920";
                break;
            case 28: unicode = "\ue921";
                break;
            case 29: unicode = "\ue922";
                break;
            case 30: unicode = "\ue923";
                break;
            case 31: unicode = "\ue924";
                break;
            case 32: unicode = "\ue925";
                break;
            case 33: unicode = "\ue926";
                break;
            case 34: unicode = "\ue92e";
                break;
            case 35: unicode = "\ue92f";
                break;
            case 36: unicode = "\ue930";
                break;
            case 37: unicode = "\ue931";
                break;
            case 38: unicode = "\ue909";
                break;
            case 39: unicode = "\ue90a";
                break;
            case 40: unicode = "\ue927";
                break;
            case 41: unicode = "\ue928";
                break;
            case 42: unicode = "\ue929";
                break;
            case 43: unicode = "\ue92a";
                break;
            case 44: unicode = "\ue92b";
                break;
            case 45: unicode = "\ue92c";
                break;
            case 46: unicode = "\ue92d";
                break;
            case 47: unicode = "\ue932";
                break;
            case 48: unicode = "\ue902";
                break;
            case 49: unicode = "\ue933";
                break;
            case 50: unicode = "\ue934";
                break;
            case 51: unicode = "\ue935";
                break;
            case 52: unicode = "\ue936";
                break;
            case 53: unicode = "\ue937";
                break;
            case 54: unicode = "\ue938";
                break;
            case 55: unicode = "\ue939";
                break;
            case 56: unicode = "\ue93a";
                break;
            case 57: unicode = "\ue93b";
                break;
            case 58: unicode = "\ue93c";
                break;
            case 59: unicode = "\ue900";
                break;
            case 60: unicode = "\ue901";
                break;
            case 61: unicode = "\ue941";
                break;
            case 62: unicode = "\ue942";
                break;
            case 63: unicode = "\ue943";
                break;
            case 64: unicode = "\ue944";
                break;
            case 65: unicode = "\ue945";
                break;
            case 66: unicode = "\ue946";
                break;
            case 67: unicode = "\ue947";
                break;
            case 68: unicode = "\ue948";
                break;
            case 69: unicode = "\ue949";
                break;
            case 70: unicode = "\ue94a";
                break;
            case 71: unicode = "\ue94b";
                break;
            case 72: unicode = "\ue94c";
                break;
            case 73: unicode = "\ue94d";
                break;
            case 74: unicode = "\ue94e";
                break;
            case 75: unicode = "\ue94f";
                break;
            case 76: unicode = "\ue950";
                break;
            case 77: unicode = "\ue951";
                break;
            case 78: unicode = "\ue952";
                break;
            case 79: unicode = "\ue93d";
                break;
            case 80: unicode = "\ue93e";
                break;
            case 81: unicode = "\ue93f";
                break;
            case 82: unicode = "\ue940";
                break;
            case 83: unicode = "\ue953";
                break;
            case 84: unicode = "\ue954";
                break;
            case 85: unicode = "\ue955";
                break;
            case 86: unicode = "\ue956";
                break;
            case 87: unicode = "\ue957";
                break;
            case 88: unicode = "\ue958";
                break;
            case 89: unicode = "\ue959";
                break;
            case 90: unicode = "\ue95a";
                break;
            case 91: unicode = "\ue95b";
                break;
            case 92: unicode = "\ue95c";
                break;
            case 93: unicode = "\ue95d";
                break;
            case 94: unicode = "\ue95e";
                break;
            case 95: unicode = "\ue95f";
                break;
            case 96: unicode = "\ue960";
                break;
            case 97: unicode = "\ue961";
                break;
            case 98: unicode = "\ue962";
                break;
            case 99: unicode = "\ue963";
                break;
            case 100: unicode = "\ue964";
                break;
            case 101: unicode = "\ue965";
                break;
            case 102: unicode = "\ue966";
                break;
            case 103: unicode = "\ue967";
                break;
            case 104: unicode = "\ue968";
                break;
            case 105: unicode = "\ue969";
                break;
            case 106: unicode = "\ue96a";
                break;
            case 107: unicode = "\ue96b";
                break;
            case 108: unicode = "\ue96c";
                break;
            case 109: unicode = "\ue96d";
                break;
            case 110: unicode = "\ue96e";
                break;
            case 111: unicode = "\ue96f";
                break;
            case 112: unicode = "\ue970";
                break;
            case 113: unicode = "\ue971";
                break;
            case 114: unicode = "\ue972";
                break;
        }
        return unicode;
    }

    public static String getChapterIconValue(int chapterNumber) {
        String value = null;
        switch (chapterNumber) {
            case 1: value = "ﮍ";
                break;
            case 2: value = "ﮎ";
                break;
            case 3: value = "ﮏ";
                break;
            case 4: value = "ﮐ";
                break;
            case 5: value = "ﮑ";
                break;
            case 6: value = "ﮒ";
                break;
            case 7: value = "ﮓ";
                break;
            case 8: value = "ﮔ";
                break;
            case 9: value = "ﮕ";
                break;
            case 10: value = "ﮖ";
                break;
            case 11: value = "ﮗ";
                break;
            case 12: value = "ﮘ";
                break;
            case 13: value = "ﮙ";
                break;
            case 14: value = "ﮚ";
                break;
            case 15: value = "ﮛ";
                break;
            case 16: value = "ﮜ";
                break;
            case 17: value = "ﮝ";
                break;
            case 18: value = "ﮞ";
                break;
            case 19: value = "ﮟ";
                break;
            case 20: value = "ﮠ";
                break;
            case 21: value = "ﮡ";
                break;
            case 22: value = "ﮢ";
                break;
            case 23: value = "ﮣ";
                break;
            case 24: value = "ﮤ";
                break;
            case 25: value = "ﮥ";
                break;
            case 26: value = "ﮦ";
                break;
            case 27: value = "ﮧ";
                break;
            case 28: value = "ﮨ";
                break;
            case 29: value = "ﮩ";
                break;
            case 30: value = "ﮪ";
                break;
            case 31: value = "ﮫ";
                break;
            case 32: value = "ﮬ";
                break;
            case 33: value = "ﮭ";
                break;
            case 34: value = "ﮮ";
                break;
            case 35: value = "ﮯ";
                break;
            case 36: value = "ﮰ";
                break;
            case 37: value = "ﮱ";
                break;
            case 38: value = "ﯓ";
                break;
            case 39: value = "ﯔ";
                break;
            case 40: value = "ﯕ";
                break;
            case 41: value = "ﯖ";
                break;
            case 42: value = "ﯗ";
                break;
            case 43: value = "ﯘ";
                break;
            case 44: value = "ﯙ";
                break;
            case 45: value = "ﯚ";
                break;
            case 46: value = "ﯛ";
                break;
            case 47: value = "ﯜ";
                break;
            case 48: value = "ﯝ";
                break;
            case 49: value = "ﯞ";
                break;
            case 50: value = "ﯟ";
                break;
            case 51: value = "ﯠ";
                break;
            case 52: value = "ﯡ";
                break;
            case 53: value = "ﯢ";
                break;
            case 54: value = "ﯣ";
                break;
            case 55: value = "ﯤ";
                break;
            case 56: value = "ﯥ";
                break;
            case 57: value = "ﯦ";
                break;
            case 58: value = "ﯧ";
                break;
            case 59: value = "ﯨ";
                break;
            case 60: value = "ﯩ";
                break;
            case 61: value = "ﯪ";
                break;
            case 62: value = "ﯫ";
                break;
            case 63: value = "ﯬ";
                break;
            case 64: value = "ﯭ";
                break;
            case 65: value = "ﯮ";
                break;
            case 66: value = "ﯯ";
                break;
            case 67: value = "ﯰ";
                break;
            case 68: value = "ﯱ";
                break;
            case 69: value = "ﯲ";
                break;
            case 70: value = "ﯳ";
                break;
            case 71: value = "ﯴ";
                break;
            case 72: value = "ﯵ";
                break;
            case 73: value = "ﯶ";
                break;
            case 74: value = "ﯷ";
                break;
            case 75: value = "ﯸ";
                break;
            case 76: value = "ﯹ";
                break;
            case 77: value = "ﯺ";
                break;
            case 78: value = "ﯻ";
                break;
            case 79: value = "ﯼ";
                break;
            case 80: value = "ﯽ";
                break;
            case 81: value = "ﯾ";
                break;
            case 82: value = "ﯿ";
                break;
            case 83: value = "ﰀ";
                break;
            case 84: value = "ﰁ";
                break;
            case 85: value = "ﰂ";
                break;
            case 86: value = "ﰃ";
                break;
            case 87: value = "ﰄ";
                break;
            case 88: value = "ﰅ";
                break;
            case 89: value = "ﰆ";
                break;
            case 90: value = "ﰇ";
                break;
            case 91: value = "ﰈ";
                break;
            case 92: value = "ﰉ";
                break;
            case 93: value = "ﰊ";
                break;
            case 94: value = "ﰋ";
                break;
            case 95: value = "ﰌ";
                break;
            case 96: value = "ﰍ";
                break;
            case 97: value = "ﰎ";
                break;
            case 98: value = "ﰏ";
                break;
            case 99: value = "ﰐ";
                break;
            case 100: value = "ﰑ";
                break;
            case 101: value = "ﰒ";
                break;
            case 102: value = "ﰓ";
                break;
            case 103: value = "ﰔ";
                break;
            case 104: value = "ﰕ";
                break;
            case 105: value = "ﰖ";
                break;
            case 106: value = "ﰗ";
                break;
            case 107: value = "ﰘ";
                break;
            case 108: value = "ﰙ";
                break;
            case 109: value = "ﰚ";
                break;
            case 110: value = "ﰛ";
                break;
            case 111: value = "ﰜ";
                break;
            case 112: value = "ﰝ";
                break;
            case 113: value = "ﰞ";
                break;
            case 114: value = "ﰟ";
                break;
        }
        return value;
    }

    public static boolean doesVerseRangeEqualWhole(QuranMeta quranMeta, int chapterNo, int fromVerse, int toVerse) {
        return fromVerse == 1 && toVerse == quranMeta.getChapterVerseCount(chapterNo);
    }

    public static Pair<Integer, Integer> correctVerseInRange(QuranMeta quranMeta, int chapterNo, Pair<Integer, Integer> range) {
        int count = quranMeta.getChapterVerseCount(chapterNo);

        return new Pair<>(Math.max(range.getFirst(), 1), Math.min(range.getSecond(), count));
    }

    public static Pair<Integer, Integer> swapVerseRangeIfNeeded(Pair<Integer, Integer> range) {
        if (range.getFirst() > range.getSecond()) {
            return new Pair<>(range.getSecond(), range.getFirst());
        }
        return range;
    }

    public static boolean isVerseInRange(int verseNo, Pair<Integer, Integer> range) {
        return verseNo >= range.getFirst() && verseNo <= range.getSecond();
    }

    public static boolean doesRangeDenoteSingle(Pair<Integer, Integer> range) {
        return doesRangeDenoteSingle(range.getFirst(), range.getSecond());
    }

    public static boolean doesRangeDenoteSingle(int fromVerse, int toVerse) {
        return fromVerse == toVerse;
    }


    public static void intRangeIterateWithIndex(Pair<Integer, Integer> range, IterationItemCatcherWithIndex itemCatcher) {
        intRangeIterateWithIndex(range.getFirst(), range.getSecond(), itemCatcher);
    }

    public static void intRangeIterateWithIndex(int from, int to, IterationItemCatcherWithIndex itemCatcher) {
        for (int currentItem = from, i = 0; currentItem <= to; currentItem++) {
            itemCatcher.currentItem(i, currentItem);
            i++;
        }
    }

    public static void iterateChapterNo(IterationItemCatcher itemCatcher) {
        IntStream.rangeClosed(1, QuranMeta.totalChapters()).forEach(itemCatcher::currentItem);
    }

    public static void iterateJuzNo(IterationItemCatcher itemCatcher) {
        IntStream.rangeClosed(1, QuranMeta.totalJuzs()).forEach(itemCatcher::currentItem);
    }

    public interface IterationItemCatcher {
        void currentItem(int currentInt);
    }

    public interface IterationItemCatcherWithIndex {
        void currentItem(int index, int currentInt);
    }
}
