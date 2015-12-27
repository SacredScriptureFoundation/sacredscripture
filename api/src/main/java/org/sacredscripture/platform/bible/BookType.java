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

import org.sacredscripturefoundation.commons.Coded;
import org.sacredscripturefoundation.commons.entity.Entity;
import org.sacredscripturefoundation.commons.locale.entity.LocalizableContainer;

/**
 * This interface defines a {@link Book} in absolute terms. This is necessary
 * because some books have been historically titled differently, combined with
 * other books, or split into smaller books. Because this is to be expected
 * among bible editions, this type allows tracking books across variations.
 * <p>
 * This is a pure lookup entity. All instances can be cached permanently.
 *
 * @author Paul Benedict
 * @see Book
 * @see BookTypeLocalization
 * @since Sacred Scripture Platform 1.0
 */
public interface BookType extends Entity<Long>, LocalizableContainer<BookTypeLocalization>, Coded<String> {

    /**
     * Compares the specified object to this instance. The object is equal if it
     * is an instance of {@link BookType} and its code is equal to this
     * instance's code.
     *
     * @param obj the object to compare
     * @return {@code true} if codes are equal; otherwise {@code false}
     * @see #getCode()
     */
    @Override
    boolean equals(Object obj);

    /**
     * Retrieves the grouping of this book type.
     *
     * @return the book type group
     */
    BookTypeGroup getBookTypeGroup();

    /**
     * Retrieves the canonical order of this book type, with a higher value
     * meaning greater in terms of sorting.
     *
     * @return the order value
     * @see #setOrder(int)
     */
    int getOrder();

    /**
     * Calculates the hash of this instance. The hash is based on the code.
     *
     * @return the hash code
     */
    @Override
    public int hashCode();

    void setBookTypeGroup(BookTypeGroup bookTypeGroup);

    /**
     * Stores the new order value for this book type.
     *
     * @param order the order value
     * @throws IllegalArgumentException if value is negative
     * @see #getOrder()
     */
    void setOrder(int order);

}
