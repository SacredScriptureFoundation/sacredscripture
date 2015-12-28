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

import org.sacredscripturefoundation.commons.Coded;
import org.sacredscripturefoundation.commons.ParentProvider;
import org.sacredscripturefoundation.commons.entity.Entity;
import org.sacredscripturefoundation.commons.locale.entity.LocalizableContainer;

import java.util.List;

/**
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
public interface BookTypeGroup
        extends
            Entity<Long>,
            LocalizableContainer<BookTypeGroupLocalization>,
            ParentProvider<BookTypeGroup>,
            Coded<String> {

    void addBookType(BookType type);

    /**
     * Adds the specified group as a child to this group. In turn, the group's
     * parent backreference will be set to this instance and to the correct
     * ordered position.
     *
     * @param group the group to add
     * @throws NullPointerException if group is {@code null}
     * @throws IllegalArgumentException if group is this instance
     * @see #getChildren()
     */
    void addChild(BookTypeGroup group);

    /**
     * Retrieves the list of books types belonging to this group.
     *
     * @return list of books types (never {@code null})
     */
    List<BookType> getBookTypes();

    List<BookTypeGroup> getChildren();

    /**
     * Retrieves the code that uniquely identifies this group.
     *
     * @return the code
     * @see #setCode(String)
     */
    @Override
    String getCode();

    /**
     * Retrieves the ordered position of this book among its sibilings.
     *
     * @return the order
     * @see #setOrder(int)
     */
    int getOrder();

    /**
     * Stores the new code for this group. This method normalizes the code value
     * to lowercase.
     *
     * @param code the code
     * @see #getCode()
     */
    void setCode(String code);

    void setOrder(int order);

    void setParent(BookTypeGroup parent);

}