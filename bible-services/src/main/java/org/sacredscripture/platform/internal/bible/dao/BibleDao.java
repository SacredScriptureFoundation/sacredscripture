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

import org.sacredscripture.platform.bible.Bible;
import org.sacredscripture.platform.internal.bible.BibleImpl;

import org.sacredscripturefoundation.commons.entity.dao.Dao;

import java.util.List;
import java.util.Locale;

/**
 * This interface defines persistence operations for {@link BibleImpl} entities.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
public interface BibleDao extends Dao<Bible, Long> {

    /**
     * Queries for the bible of the specified code. The code comparison is not
     * case-sensitive.
     *
     * @param code the code
     * @return the found bible or {@code null}
     * @throws NullPointerException if code is {@code null}
     * @see Bible#getCode()
     */
    Bible findByCode(String code);

    /**
     * Queries for any bibles that are written in the specified locale.
     *
     * @param locale the locale to match
     * @return list of found bibles (never {@code null})
     */
    List<Bible> findByLocale(Locale locale);

    /**
     * Queries for the bible flagged as the system default.
     *
     * @return the found bible or {@code null}
     * @see Bible#isDefault()
     */
    Bible findDefault();

}
