/*
 * Copyright (c) 2015, 2016 Sacred Scripture Foundation.
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

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

public abstract class AbstractHypermediaBean {

    @XmlElement(name = "links")
    private List<ResourceLinkBean> links;

    /**
     * Adds the specified link to this bean.
     *
     * @param link the link to add
     * @throws NullPointerException if the link or its {@code href} is
     * {@code null}
     * @see #addLink(String, String)
     * @see #addLink(String, String, Locale)
     * @see #addLink(String, String, String)
     * @see #addLink(String, String, Locale, String)
     */
    public void addLink(ResourceLinkBean link) {
        Objects.requireNonNull(link);
        Objects.requireNonNull(link.getHref());
        if (links == null) {
            links = new LinkedList<ResourceLinkBean>();
        }
        links.add(link);
    }

    /**
     * Constructs a new {@link ResourceLinkBean} instance with the specified
     * values and adds it to this bean.
     *
     * @param href the web address
     * @param rel the relationship type (can be {@code null})
     * @throws NullPointerException if {@code href} is {@code null}
     * @see #addLink(ResourceLinkBean)
     * @see #addLink(String, String, Locale)
     * @see #addLink(String, String, String)
     * @see #addLink(String, String, Locale, String)
     */
    public void addLink(String href, String rel) {
        addLink(href, rel, null, null);
    }

    public void addLink(String href, String rel, Locale lang) {
        addLink(href, rel, lang, null);
    }

    public void addLink(String href, String rel, Locale lang, String title) {
        ResourceLinkBean link = new ResourceLinkBean();
        link.setHref(Objects.requireNonNull(href));
        link.setRel(rel);
        link.setLocale(lang);
        link.setTitle(title);
        addLink(link);
    }

    public void addLink(String href, String rel, String title) {
        addLink(href, rel, null, title);
    }

    public List<ResourceLinkBean> getLinks() {
        return links;
    }

    /**
     * Retrieves the first link that has the specified relationship value.
     *
     * @param rel the relationship value
     * @return the found link or {@code null}
     * @see #getLinks()
     */
    public ResourceLinkBean ofRelFirst(String rel) {
        for (ResourceLinkBean link : getLinks()) {
            if (rel.equals(link.getRel())) {
                return link;
            }
        }
        return null;
    }

}
