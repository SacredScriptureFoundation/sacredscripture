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

import org.sacredscripturefoundation.commons.Coded;
import org.sacredscripturefoundation.commons.Named;
import org.sacredscripturefoundation.commons.entity.Entity;
import org.sacredscripturefoundation.commons.locale.LocaleSensitive;

import java.util.List;

/**
 * This interface defines a bible book.
 *
 * @author Paul Benedict
 * @see Bible#getBooks()
 * @see BookGroup#getBooks()
 * @since Sacred Scripture Platform 1.0
 */
public interface Book extends Entity<Long>, Named, Coded<String> {

    /**
     * Retrieves the abbreviations of this book.
     *
     * @return the abbreviations
     */
    @LocaleSensitive
    List<String> getAbbreviations();

    /**
     * Retrieves the owning bible of this book.
     *
     * @return the bible
     */
    Bible getBible();

    /**
     * Retrieves the collection of chapters that belong to this book. The
     * collection is sorted accordingly to the bible edition.
     *
     * @return the collection
     */
    List<Chapter> getChapters();

    /**
     * Retrieves the internal code of this book.
     *
     * @return the bible book code
     */
    @Override
    String getCode();

    /**
     * Returns the formal name of this book. The formal name is a long
     * description like "The Gospel of Jesus Christ according to Matthew".
     *
     * @return the formal name
     */
    @LocaleSensitive
    String getFormalName();

    /**
     * Retrieves the typical name of this book. The typical name is the name
     * which the book is commonly known as in everyday life.
     *
     * @return the name
     * @see #getFormalName()
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
     */
    int getOrder();

}