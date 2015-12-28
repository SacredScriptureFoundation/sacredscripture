/*
 * Copyright (c) 2013, 2015 Sacred Scripture Foundation.
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
package org.sacredscripture.platform.bible.canon;

import org.sacredscripturefoundation.commons.Named;
import org.sacredscripturefoundation.commons.entity.Entity;
import org.sacredscripturefoundation.commons.locale.LocaleProvider;

import java.util.List;
import java.util.Locale;

/**
 * This interface defines the properties of a {@link BookType} that must be
 * translated into each supported locale.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
public interface BookTypeLocalization extends Entity<Long>, Named, LocaleProvider {

    /**
     * Adds a new abbreviation to this localization. The order is important. The
     * first is the primary abbreviation and is likely to always be selected for
     * display purposes.
     *
     * @see #getAbbreviations()
     * @see #setAbbreviations(List)
     */
    void addAbbreviation(String abbreviation);

    /**
     * Retrieves the abbreviations of this localization. At least one
     * abbreviation is guaranteed to exist.
     *
     * @return the abbreviations (never {@code null})
     * @see #addAbbreviation(String)
     * @see #setAbbreviations(List)
     */
    List<String> getAbbreviations();

    /**
     * Retrieves the owning book type of this localization.
     *
     * @return the type
     * @see #setBookType(BookType)
     */
    BookType getBookType();

    /**
     * Retrieves the common name of this localization. Such names are, for
     * example, "Matthew" and "Revelation".
     */
    @Override
    String getName();

    /**
     * Retrieves the title of this localization. Such titles are, for example,
     * "The Gospel According to Matthew" and
     * "The Book of Revelation by John of Patmos".
     *
     * @return the title
     * @see #setTitle(String)
     */
    String getTitle();

    void setAbbreviations(List<String> abbreviations);

    /**
     * Stores the new book type for this localization.
     *
     * @param type the type
     * @see #getBookType()
     */
    void setBookType(BookType type);

    /**
     * Stores the new locale for this localization.
     *
     * @param locale the locale
     * @see #getLocale()
     */
    void setLocale(Locale locale);

    /**
     * Stores the common name of this localization.
     *
     * @param name the name
     * @see #getName()
     */
    void setName(String name);

    /**
     * Stores the new title for this localization.
     *
     * @param title the title
     * @see #getTitle()
     */
    void setTitle(String title);

}
