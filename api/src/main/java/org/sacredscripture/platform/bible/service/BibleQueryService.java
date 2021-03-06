/*
 * Copyright (c) 2015 Sacred Scripture Foundation.
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
package org.sacredscripture.platform.bible.service;

import org.sacredscripture.platform.bible.Bible;
import org.sacredscripture.platform.bible.Chapter;
import org.sacredscripture.platform.bible.Content;
import org.sacredscripture.platform.bible.TableOfContents;

import java.util.List;
import java.util.Locale;

/**
 * This interface defines a service for querying Bible editions and their
 * contents.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
public interface BibleQueryService {

    /**
     * Retrieves the bible of the specified code.
     *
     * @param bibleCode the bible's code
     * @return the found bible or {@code null}
     * @see Bible#getCode()
     * @see #getBibleByPublicId(String)
     */
    Bible getBibleByCode(String bibleCode);

    /**
     * Retrieves the bible of the specified public identifier.
     *
     * @param bibleId the public identifier of the bible
     * @return the found bible or {@code null}
     * @see Bible#getPublicId()
     * @see #getBibleByCode(String)
     */
    Bible getBibleByPublicId(String bibleId);

    /**
     * Retrieves the list of bibles. If {@code bibleLocale} is not {@code null},
     * only the bibles that match this locale will be retrieved; otherwise all
     * bibles are retrieved.
     *
     * @param bibleLocale the filtering locale (can be {@code null})
     * @return the list of found bibles or empty list
     */
    List<Bible> getBibles(Locale bibleLocale);

    /**
     * Retrieves the chapters of the specified bible and book. The chapters are
     * sorted in the order of appearance in the book.
     *
     * @param bibleId the public identifier of the bible
     * @param bookPos the ordinal position of the book
     * @return the list of found chapters or empty list
     */
    List<Chapter> getChapters(String bibleId, int bookPos);

    Content getContent(String contentId);

    /**
     * Retrieves the table of contents for the specified bible.
     *
     * @param bibleCode the bible's code
     * @return the table of contents or {@code null} if bible not found
     */
    TableOfContents getTableOfContents(String bibleCode);

}
