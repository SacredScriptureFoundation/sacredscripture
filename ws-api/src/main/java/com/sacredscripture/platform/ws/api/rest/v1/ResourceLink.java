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
package com.sacredscripture.platform.ws.api.rest.v1;

import org.sacredscripturefoundation.commons.locale.LocaleProvider;

import java.util.Locale;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents a link to an external resource.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
@XmlRootElement
public class ResourceLink implements LocaleProvider {

    @XmlElement(name = "href")
    private String href;
    @XmlElement(name = "hreflang")
    private Locale locale;
    @XmlElement(name = "rel")
    private String rel;

    /**
     * Retrieves the web address of this link.
     *
     * @return the web address
     * @see #setHref(String)
     */
    public String getHref() {
        return href;
    }

    /**
     * @see #setLocale(Locale)
     */
    @Override
    public Locale getLocale() {
        return locale;
    }

    /**
     * Retrieves the relationship of this link.
     *
     * @return the relationship value
     * @see #setRel(String)
     * @see StandardLinkRelation
     */
    public String getRel() {
        return rel;
    }

    /**
     * Stores the new web address for this link. No verification is performed to
     * determine whether the address is a well-formed URL/URI.
     *
     * @param href the web address
     * @see #getHref()
     */
    public void setHref(String href) {
        this.href = href;
    }

    /**
     * Stores the new locale for this link.
     *
     * @param locale the locale
     * @see #getLocale()
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * Stores the new relationship for this link.
     *
     * @param rel the relationship value
     * @see #getRel()
     * @see StandardLinkRelation
     */
    public void setRel(String rel) {
        this.rel = rel;
    }

}
