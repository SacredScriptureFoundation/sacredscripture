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
import org.sacredscripturefoundation.commons.locale.LocaleSensitive;

import java.awt.print.Book;
import java.util.List;
import java.util.Locale;

/**
 * This interface defines an edition of the bible.
 *
 * @author Paul Benedict
 * @since 1.0
 */
public interface Bible extends Entity<Long>, Named {

    /**
     * Retrieves the abbreviation of this bible edition. The abbreviation is a
     * shortened textual description of the <code>name</code> property (e.g.,
     * "RSV-CE").
     *
     * @return the abbreviation
     */
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
     * @return the collection; never {@code null}
     */
    List<Book> getBooks();

    /**
     * Retrieves the locale of this bible edition. The locale can be used, for
     * instance, in sorting bible versions by language.
     *
     * @return the locale
     */
    Locale getLocale();

    /**
     * Retrieves the name of this bible edition (e.g., "King James Version").
     *
     * @return the name
     */
    @Override
    @LocaleSensitive
    String getName();

    /**
     * Retrieves the title of this bible edition. The title is such as
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

}