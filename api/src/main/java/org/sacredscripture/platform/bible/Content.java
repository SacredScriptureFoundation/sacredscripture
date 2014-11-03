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
package org.sacredscripture.platform.bible;

import org.sacredscripturefoundation.commons.entity.Entity;

/**
 * This interface represents an ordered piece of content within a Bible edition.
 * The text must be in the language of its owning Bible, and the content type is
 * indicated by a type code.
 * <p>
 * While it may seem reasonable (at first) to organize contents simply by
 * ordering chapter-verse coordinates, the approach fails to capture the
 * historical aberrations among editions. Some of the complexities include
 * out-of-order verse numbering and extra-biblical annotations (e.g., chapter,
 * verse, footnote, etc.). A content system that is truly rendering a manuscript
 * must take these into consideration.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
public interface Content extends Entity<Long> {

    /**
     * Retrieves the owning book of this content.
     *
     * @return the owning book
     * @see #setBook(Book)
     */
    Book getBook();

    /**
     * Retrieves the kind of content. This should be used to determine the
     * appropriate cast to a subinterface.
     * <p>
     * No corresponding setter is necessary. Subclass implementations already
     * know what kind they are.
     *
     * @return the kind
     */
    ContentKind getContentKind();

    ContentType getContentType();

    /**
     * Retrieves the order value of this content, with a higher value meaning
     * greater in terms of sorting.
     *
     * @return the order value
     * @see #setOrder(int)
     */
    int getOrder();

    /**
     * Stores the new owning book for this content.
     *
     * @param book the book
     * @see #getBook()
     */
    void setBook(Book book);

    void setContentType(ContentType type);

    /**
     * Stores the new order value of this content.
     *
     * @param order the order
     * @throws IllegalArgumentException if order is &lt; 0
     * @see #getOrder()
     */
    void setOrder(int order);

}
