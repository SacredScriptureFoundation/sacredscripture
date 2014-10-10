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
import org.sacredscripturefoundation.commons.locale.LocaleProvider;

/**
 * This interface defines the properties of a {@link Bible} that are translated
 * into each acceptable locale.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
public interface BibleLocalizedProperties extends Entity<Long>, Named, LocaleProvider {

    /**
     * Retrieves the abbreviation of this localized bible properties. The
     * abbreviation is a shortened textual description of the <code>name</code>
     * property (e.g., "RSV-CE").
     *
     * @return the abbreviation
     * @see #setAbbreviation(String)
     */
    String getAbbreviation();

    /**
     * Retrieves the copyright notice of this localized bible properties.
     *
     * @return the copyright
     * @see #setCopyrightNotice(String)
     */
    String getCopyrightNotice();

    /**
     * Retrieves the license agreement of this localized bible properties.
     *
     * @return the license
     * @see #setLicense(String)
     */
    String getLicense();

    /**
     * Retrieves the title of this bible edition. The title is such as
     * "The Holy Bible" or "Sacra Biblia".
     *
     * @return the title
     * @see #setTitle(String)
     */
    String getTitle();

    /**
     * Stores the new abbreviation for this localized bible properties.
     *
     * @param abbreviation the abbreviation
     * @see #getAbbreviation()
     */
    void setAbbreviation(String abbreviation);

    /**
     * Stores the new copyright for this localized bible properties.
     *
     * @param copyright the copyright notice
     * @see #getCopyrightNotice()
     */
    void setCopyrightNotice(String copyright);

    /**
     * Stores the new licese for thsi localized bible properties.
     *
     * @param license the license
     * @see #getLicense()
     */
    void setLicense(String license);

    /**
     * Stores the new name for this localized bible properties.
     *
     * @param name the name
     * @see #getName()
     */
    void setName(String name);

    /**
     * Stores the new title for this localized bible properties.
     *
     * @param title the title
     * @see #getTitle()
     */
    void setTitle(String title);

}
