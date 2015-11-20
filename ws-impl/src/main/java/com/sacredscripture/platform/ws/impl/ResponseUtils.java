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
package com.sacredscripture.platform.ws.impl;

import org.sacredscripturefoundation.commons.InstantiationNotIntendedError;

import java.net.URI;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriBuilder;

public final class ResponseUtils {

    /**
     * Creates a standard "alternate" (link relationship) URI by appending a
     * {@code lang} parameter with the specified locale value plus any
     * additional values.
     *
     * @param uriBuilder the URI builder
     * @param locale the alternate locale
     * @param values any other values
     * @return the URI
     */
    public static URI alternateLocalizedUri(UriBuilder uriBuilder, Locale locale, Object... values) {
        return uriBuilder.queryParam("lang", locale).build(values);
    }

    public static Response multipleChoices(Locale primaryLocale, Set<Locale> otherLocales, UriBuilder uriBuilder, Object... values) {
        // Build redirect location if possible
        URI location = null;
        if (uriBuilder != null) {
            location = alternateLocalizedUri(uriBuilder, primaryLocale, values);
        }

        // Build choices
        LinkedHashSet<Locale> set = new LinkedHashSet<Locale>();
        set.add(primaryLocale);
        set.addAll(otherLocales);
        Object[] localesArray = set.toArray();

        // Add location if available
        ResponseBuilder rb = Response.status(300).entity(localesArray);
        if (location != null) {
            rb.location(location);
        }

        return rb.build();
    }

    /**
     * No instances possible.
     */
    private ResponseUtils() {
        throw new InstantiationNotIntendedError();
    }

}
