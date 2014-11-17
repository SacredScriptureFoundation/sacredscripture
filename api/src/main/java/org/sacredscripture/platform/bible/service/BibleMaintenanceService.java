/*
 * Copyright (c) 2014 Sacred Scripture Foundation.
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
import org.sacredscripture.platform.bible.Book;
import org.sacredscripture.platform.bible.BookType;
import org.sacredscripture.platform.bible.BookTypeGroup;
import org.sacredscripture.platform.bible.BookTypeGroupLocalization;
import org.sacredscripture.platform.bible.BookTypeLocalization;

import org.sacredscripturefoundation.commons.entity.DuplicateEntityException;
import org.sacredscripturefoundation.commons.entity.UnknownEntityException;

/**
 * This interface defines privileged operations to administer the bible system.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
public interface BibleMaintenanceService {

    /**
     * Adds the specified book to a bible.
     *
     * @param req the request message
     * @return the new book
     * @throws UnknownEntityException if any codes cannot be found
     * @throws DuplicateEntityException if the book type code is already in use
     * for the specified bible
     */
    Book add(AddBookRequest req);

    /**
     * Creates and persists a new {@link BookTypeGroup} instance.
     *
     * @param req the request message
     * @return the new group
     * @throws UnknownEntityException if the specified parent cannot be found
     * @throws DuplicateEntityException if the book type code is already in use
     * @see #save(SaveBookTypeLocalizationRequest)
     */
    BookTypeGroup add(AddBookTypeGroupRequest req);

    /**
     * Creates and persists a new {@link BookType} instance.
     *
     * @param req the request message
     * @return the new book type
     * @throws UnknownEntityException if the group cannot be found
     * @throws DuplicateEntityException if the group code is already in use
     * @see #save(SaveBookTypeLocalizationRequest)
     */
    BookType add(AddBookTypeRequest req);

    /**
     * Retrieves the bible by the specified internal code.
     *
     * @param bibleCode the internal bible code
     * @return the found bible or {@code null}
     */
    Bible findBibleByCode(String bibleCode);

    /**
     * Inserts of updates the bible and its default localized data.
     *
     * @param req the request message
     * @return the new or updated bible
     */
    Bible save(SaveBibleRequest req);

    /**
     * Inserts or updates the localized data for the specified group.
     *
     * @param req the request message
     * @return the localized data
     * @throws UnknownEntityException if the specified group cannot be found
     * @see #add(AddBookTypeGroupRequest)
     */
    BookTypeGroupLocalization save(SaveBookTypeGroupLocalizationRequest req);

    /**
     * Inserts or updates the localized data for the specified book type.
     *
     * @param req the request message
     * @return the localized data
     * @throws UnknownEntityException if the specified type cannot be found
     * @see #add(AddBookTypeRequest)
     */
    BookTypeLocalization save(SaveBookTypeLocalizationRequest req);

}
