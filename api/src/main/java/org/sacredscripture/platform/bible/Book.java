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
package org.sacredscripture.platform.bible;

import org.sacredscripturefoundation.commons.Named;
import org.sacredscripturefoundation.commons.entity.Entity;
import org.sacredscripturefoundation.commons.locale.LocaleSensitive;

import java.util.List;

/**
 * This interface defines a bible book inside a particular bible edition.
 *
 * @author Paul Benedict
 * @see Bible#getBooks()
 * @since Sacred Scripture Platform 1.0
 */
public interface Book extends Entity<Long>, Named {

    void addContent(Content content);

    /**
     * Retrieves the primary localized abbreviation of this book.
     *
     * @return the abbreviation
     * @see #getAbbreviations()
     */
    @LocaleSensitive
    String getAbbreviation();

    /**
     * Retrieves the localized abbreviations of this book. The order is
     * important. The first one is the primary abbreviation, the second is
     * secondary, and so forth. The first should be used when only one is to be
     * displayed to the user.
     *
     * @return the abbreviations
     * @see #getAbbreviation()
     */
    @LocaleSensitive
    List<String> getAbbreviations();

    /**
     * Retrieves the owning bible of this book.
     *
     * @return the bible
     * @see #setBible(Bible)
     */
    Bible getBible();

    /**
     * Retrieves the type of this book.
     *
     * @return the book type
     */
    BookType getBookType();

    /**
     * Retrieves the list of chapters in this book. This method is a convenient
     * filter of the {@link #getContents() contents}.
     *
     * @return the list of chapters (never {@code null})
     * @see #getContents()
     */
    List<Chapter> getChapters();

    /**
     * Retrieves the collection of content that belong to this book. The
     * collection is sorted to the "reading order" of the bible edition.
     * <p>
     * It is highly unusual for any client code to invoke this since the data
     * set is very large. Implementations of this type must stream the contents
     * (i.e., inflating individual content at access time) to ever make this
     * method useful lest an {@link OutOfMemoryError} occur.
     *
     * @return the contents
     * @see #addContent(Content)
     */
    // XXX refactor into Contents with chapters() filter?
    List<Content> getContents();

    /**
     * Retrieves the localized common name of this book. The common name is the
     * name which the book is commonly known as in everyday life.
     *
     * @return the name
     */
    @Override
    @LocaleSensitive
    String getName();

    /**
     * Retrieves the order value of this book, with a higher value meaning
     * greater in terms of sorting. The meaning of the order is defined by the
     * canonical positioning within its owning Bible.
     *
     * @return the order value
     * @see #previous()
     * @see #next()
     */
    int getOrder();

    /**
     * Returns the localized title of this book. The title is a long description
     * like "The Gospel of Jesus Christ according to Matthew".
     *
     * @return the formal name
     */
    @LocaleSensitive
    String getTitle();

    /**
     * Retrieves the book in the associated bible that proceeds this book
     * according to canonical ordering.
     *
     * @return the next book or {@code null} if last book
     * @see #previous()
     * @see #getOrder()
     */
    Book next();

    /**
     * Retrieves the book in the associated bible that preceeds this book
     * according to canonical ordering.
     *
     * @return the previous book or {@code null} if first book
     * @see #next()
     * @see #getOrder()
     */
    Book previous();

    /**
     * Stores the new bible owner for this book.
     *
     * @param bible the bible
     * @see #getBible()
     */
    void setBible(Bible bible);

    void setBookType(BookType type);

}