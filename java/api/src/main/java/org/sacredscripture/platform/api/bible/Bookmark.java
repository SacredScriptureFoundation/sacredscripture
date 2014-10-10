/*
 * Copyright (c) 2013, 2014 Sacred Scripture Foundation.
 * "All scripture is given by inspiration of God, and is profitable for
 * doctrine, for reproof, for correction, for instruction in righteousness:
 * That the man of God may be perfect, throughly furnished unto all good
 * works." (2 Tim 3:16-17)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sacredscripture.platform.api.bible;

/**
 * This interface provides an associative path from a {@link Bible} to a
 * {@link Book} to a {@link Chapter} and to a {@link Verse}. Not all objects
 * must be present, but they must be present in their logical order (verses must
 * have a chapter, etc.)
 *
 * @author Paul Benedict
 * @see org.sacredscripture.platform.api.bible.Bible
 * @see org.sacredscripture.platform.api.bible.Book
 * @see org.sacredscripture.platform.api.bible.Chapter
 * @see org.sacredscripture.platform.api.bible.Verse
 * @since 1.0
 */
public interface Bookmark {

    Bible getBible();

    /**
     * Retrieves the book of this bookmark.
     *
     * @return the book
     */
    Book getBook();

    /**
     * Retrieves the chapter of this bookmark.
     *
     * @return the chapter
     */
    Chapter getChapter();

    /**
     * Retrieves the verse of this bookmark.
     *
     * @return the verse
     */
    Verse getVerse();

    /**
     * Determines whether this bookmark is book-based; that is, it carries all
     * the information necessary to form a complete line-of-sight up to a
     * {@link Book} and no further.
     *
     * @return {@code true} if book-based; otherwise {@code false}
     */
    boolean isBookBased();

    /**
     * Determines whether this bookmark is chapter-based; that is, it carries
     * all the information necessary to form a complete line-of-sight up to a
     * {@link Chapter} and no further.
     *
     * @return {@code true} if chapter-based; otherwise {@code false}
     */
    boolean isChapterBased();

    /**
     * Determines whether this bookmark is verse-based; that is, it carries all
     * the information necessary to form a complete line-of-sight up to a
     * {@link Verse} and no further.
     *
     * @return {@code true} if verse-based; otherwise {@code false}
     */
    boolean isVerseBased();

}