/*
 * (c) Faisal Khan. Created on 20/11/2021.
 */

package com.goran.quran.karim.dang.interfaceUtils;

import com.goran.quran.karim.dang.components.bookmark.BookmarkModel;

public interface BookmarkCallbacks {
    void onBookmarkRemoved(BookmarkModel model);

    default void onBookmarkAdded(BookmarkModel model) {}

    default void onBookmarkUpdated(BookmarkModel model) {}
}
