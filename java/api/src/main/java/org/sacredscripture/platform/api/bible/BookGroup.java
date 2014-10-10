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

import java.util.List;

/**
 * This interface represents a grouping of books within a bible edition.
 *
 * @author Paul Benedict
 * @see Bible#getBookGroups()
 * @since 1.0
 */
public interface BookGroup extends Entity<Long>, Named {

    /**
     * Retrieves the owning bible of this group.
     *
     * @return the bible
     */
    Bible getBible();

    /**
     * Retrieves the list of books belonging to this group.
     *
     * @return list of books; never {@code null}
     */
    List<Book> getBooks();

    /**
     * Retrieves the localized name of this group. Each group must have a unique
     * name within its owning bible.
     *
     * @return the name
     */
    @Override
    @LocaleSensitive
    String getName();

}