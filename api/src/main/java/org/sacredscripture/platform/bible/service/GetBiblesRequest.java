/*
 * Copyright (c) 2015 Sacred Scripture Foundation.
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

import org.sacredscripturefoundation.commons.ServiceRequestMessage;

import java.util.Locale;

import javax.validation.constraints.NotNull;

/**
 * This class is the request to query for {@link Bible} entity.
 *
 * @author Paul Benedict
 * @see BibleQueryService#getBibles(GetBiblesRequest)
 * @since Sacred Scripture Platform 1.0
 */
public class GetBiblesRequest extends ServiceRequestMessage {

    Locale bibleLocale;
    Locale userLocale;

    /**
     * Retrieves the filtering locale for this request. If this is not
     * {@code null}, only the bibles that match this locale will be retrieved.
     * If {@code null}, all bibles are retrieved.
     *
     * @return the locale
     * @see #setBibleLocale(Locale)
     */
    public final Locale getBibleLocale() {
        return bibleLocale;
    }

    /**
     * Retrieves the locale of requesting user.
     *
     * @return the locale
     * @see #setUserLocale(Locale)
     */
    @NotNull
    public final Locale getUserLocale() {
        return userLocale;
    }

    /**
     * Stores the new filtering locale for this request.
     *
     * @param bibleLocale the bible locale
     * @see #getBibleLocale()
     */
    public final void setBibleLocale(Locale bibleLocale) {
        this.bibleLocale = bibleLocale;
    }

    /**
     * Stores the new user locale for this request.
     *
     * @param userLocale the user locale
     * @see #getUserLocale()
     */
    public final void setUserLocale(Locale userLocale) {
        this.userLocale = userLocale;
    }

}
