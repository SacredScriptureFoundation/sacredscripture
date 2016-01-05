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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

/**
 * This abstract class is the superclass for any bean which requires hypermedia
 * support to be HATEOAS compliant. To quote Spring, "HATEOAS (Hypermedia as the
 * Engine of Application State) is a constraint of the REST application
 * architecture. A hypermedia-driven site provides information to navigate the
 * site's REST interfaces dynamically by including hypermedia links with the
 * responses."
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
public abstract class AbstractHypermediaBean {

    @XmlElement(name = "links")
    private List<ResourceLinkBean> links;

    @XmlElement(name = "embedded")
    private Map<String, AbstractHypermediaBean> embedded;

    public void addEmbedded(String name, AbstractHypermediaBean bean) {
        getEmbedded().put(name, bean);
    }

    /**
     * Adds the specified link to this bean.
     * <p>
     * If a link requires minimal customization, an alternative and concise mean
     * exists in the many overloaded methods to construct and add a new link
     * instance from a few parameters.
     *
     * @param link the link to add
     * @throws NullPointerException if the link is {@code null}, or its
     * {@code href} or {@code rel} properties are {@code null}
     * @see #addLink(String, String)
     * @see #addLink(String, LinkRelation, String)
     * @see #addLink(String, String, Locale)
     * @see #addLink(String, String, String)
     * @see #addLink(String, String, Locale, String)
     */
    public void addLink(ResourceLinkBean link) {
        Objects.requireNonNull(link);
        Objects.requireNonNull(link.getHref());
        Objects.requireNonNull(link.getRel());
        getLinks().add(link);
    }

    /**
     * Constructs a new {@link ResourceLinkBean} instance with the specified web
     * address, standard relationship, and optional title; then adds it to this
     * bean.
     *
     * @param href the web address
     * @param rel the standard relationship
     * @param title the title (can be {@code null})
     * @throws NullPointerException if either {@code href} or {@code rel} are
     * {@code null}
     * @see #addLink(ResourceLinkBean)
     * @see #addLink(String, String)
     * @see #addLink(String, String, Locale)
     * @see #addLink(String, String, String)
     * @see #addLink(String, String, Locale, String)
     */
    public void addLink(String href, LinkRelation rel, String title) {
        addLink(href, rel.rel(), title);
    }

    /**
     * Constructs a new {@link ResourceLinkBean} instance with the specified web
     * address and custom relationship; then adds it to this bean.
     *
     * @param href the web address
     * @param rel the custom relationship
     * @throws NullPointerException if any arguments are {@code null}
     * @see #addLink(ResourceLinkBean)
     * @see #addLink(String, LinkRelation, String)
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
        link.setRel(Objects.requireNonNull(rel));
        link.setLocale(lang);
        link.setTitle(title);
        addLink(link);
    }

    /**
     * Constructs a new {@link ResourceLinkBean} instance with the specified web
     * address, custom relationship, and optional title; then adds it to this
     * bean.
     *
     * @param href the web address
     * @param rel the custom relationship
     * @param title the title (can be {@code null})
     * @throws NullPointerException if either {@code href} or {@code rel} are
     * {@code null}
     * @see #addLink(ResourceLinkBean)
     * @see #addLink(String, String)
     * @see #addLink(String, LinkRelation, String)
     * @see #addLink(String, String, Locale)
     * @see #addLink(String, String, Locale, String)
     */
    public void addLink(String href, String rel, String title) {
        addLink(href, rel, null, title);
    }

    /**
     * Retrieves the mappings of resource representations (full or partial)
     * embedded in this bean. Each resource is mapped to a unique name.
     *
     * @return the embedded resource mappings or empty map
     * @see #addEmbedded(String, AbstractHypermediaBean)
     */
    public Map<String, AbstractHypermediaBean> getEmbedded() {
        if (embedded == null) {
            embedded = new HashMap<>();
        }
        return embedded;
    }

    /**
     * Retrieves the links associated to this bean.
     *
     * @return the list of links or empty list
     * @see #addLink(ResourceLinkBean)
     */
    public List<ResourceLinkBean> getLinks() {
        if (links == null) {
            links = new LinkedList<ResourceLinkBean>();
        }
        return links;
    }

}
