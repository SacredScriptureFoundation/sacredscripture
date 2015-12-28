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

import java.util.Locale;

/**
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
public interface BookTypeGroupLocalization extends Entity<Long>, Named, LocaleProvider {

    /**
     * Retrieves the owning group of this localization.
     *
     * @return the group
     */
    BookTypeGroup getBookTypeGroup();

    void setBookTypeGroup(BookTypeGroup bookTypeGroup);

    /**
     * Stores the new locale for this localization.
     *
     * @param locale the locale
     * @see #getLocale()
     */
    void setLocale(Locale locale);

    void setName(String name);

}
