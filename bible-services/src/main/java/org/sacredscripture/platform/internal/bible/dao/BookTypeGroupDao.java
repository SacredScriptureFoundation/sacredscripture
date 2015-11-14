/*
 * Copyright (c) 2014, 2015 Sacred Scripture Foundation.
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
package org.sacredscripture.platform.internal.bible.dao;

import org.sacredscripture.platform.bible.BookTypeGroup;
import org.sacredscripture.platform.internal.bible.BookTypeGroupImpl;

import org.sacredscripturefoundation.commons.entity.dao.Dao;

import java.util.List;

/**
 * This interface defines persistence operations for {@link BookTypeGroupImpl}
 * entities.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
public interface BookTypeGroupDao extends Dao<BookTypeGroup, Long> {

    /**
     * Queries the group by the specified code.
     *
     * @param code the code
     * @return the found group or {@code null}
     */
    BookTypeGroup findByCode(String code);

    /**
     * Queries the groups that are the top-most level parents.
     *
     * @return the root groups (never {@code null})
     */
    List<BookTypeGroup> findRoots();

}
