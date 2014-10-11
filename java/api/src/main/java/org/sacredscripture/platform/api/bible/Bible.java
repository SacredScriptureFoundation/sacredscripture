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

import org.sacredscripturefoundation.commons.Named;
import org.sacredscripturefoundation.commons.entity.Entity;
import org.sacredscripturefoundation.commons.locale.LocaleProvider;
import org.sacredscripturefoundation.commons.locale.LocaleSensitive;
import org.sacredscripturefoundation.commons.locale.entity.LocalizableContainer;

import java.util.List;
import java.util.Locale;

/**
 * This interface defines an edition of the bible.
 *
 * @author Paul Benedict
 * @see BibleLocalization
 * @since Sacred Scripture Platform 1.0
 */
public interface Bible extends Entity<Long>, LocalizableContainer<BibleLocalization>, LocaleProvider, Named {

    /**
     * Retrieves the localized abbreviation of this bible edition. The
     * abbreviation is a shortened textual description of the <code>name</code>
     * property (e.g., "RSV-CE").
     *
     * @return the abbreviation
     */
    @LocaleSensitive
    String getAbbreviation();

    /**
     * Retrieves the collection of book groups in this bible.
     *
     * @return the list of book groups
     */
    List<BookGroup> getBookGroups();

    /**
     * Retrieves the collection of books that belong to this bible edition. The
     * collection must be sorted according to the table of contents of this
     * edition.
     *
     * @return the collection (never {@code null})
     */
    List<Book> getBooks();

    /**
     * Retrieves the localized copyright notice for this bible edition.
     *
     * @return the copyright
     */
    @LocaleSensitive
    String getCopyrightNotice();

    /**
     * Retrieves the localized license agreement for this bible edition.
     *
     * @return the license
     */
    @LocaleSensitive
    String getLicense();

    /**
     * Retrieves the native locale of this bible edition. The locale can be
     * used, for instance, in sorting bible versions by language.
     *
     * @return the locale
     * @see #setLocale(Locale)
     */
    @Override
    Locale getLocale();

    /**
     * Retrieves the localized name of this bible edition (e.g.,
     * "King James Version").
     *
     * @return the name
     */
    @Override
    @LocaleSensitive
    String getName();

    /**
     * Retrieves the localized title of this bible edition. The title is such as
     * "The Holy Bible" or "Sacra Biblia".
     *
     * @return the title
     */
    @LocaleSensitive
    String getTitle();

    /**
     * Retrieves the truth about whether this Bible edition requires
     * right-to-left reading, such as Hebrew or Arabic. When this flag is set,
     * the usual response for page rendering is to right-align whatever text is
     * associated with this Bible.
     *
     * @return {@code true} if right-to-left or {@code false}
     */
    boolean isRightToLeftReading();

    /**
     * Stores the native locale for this bible edition.
     *
     * @param locale the locale
     * @see #getLocale()
     */
    void setLocale(Locale locale);

    /**
     * Sets whether this bible edition requires right-to-left reading.
     *
     * @param rtl {@code true} for right-to-left; otherwise {@code false}
     * @see #isRightToLeftReading()
     */
    void setRightToLeftReading(boolean rtl);

}