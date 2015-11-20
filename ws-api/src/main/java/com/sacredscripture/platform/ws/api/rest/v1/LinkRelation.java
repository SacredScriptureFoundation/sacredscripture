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

/**
 * This class contains constants for standard and commonly-used link relations.
 * Use the {@link #rel()} method to get the respective literal for constructing
 * hypermedia controls (like HTML &lt;link&gt; or HAL+JSON).
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 * @see <a href=
 * "http://www.iana.org/assignments/link-relations/link-relations.xhtml">IANA
 * Link Relations</a>
 * @see <a href="http://www.w3.org/TR/html5/links.html">HTML 5 links</a>
 * @see <a href="http://microformats.org/wiki/existing-rel-values">Microformats
 * Wiki Existing Rel Values</a>
 * @see <a href="https://en.wikipedia.org/wiki/HATEOAS">HATEOAS on Wikipedia</a>
 */
public enum LinkRelation {

    /**
     * Indicates that the referenced document is the subject or topic of the
     * link's context. Multiple subjects can be indicated through the use of
     * multiple "about" link relations. For example, if the context resource is
     * a review about a particular product, the {@code "about"} link can be used
     * to reference the URL of the product
     * <p>
     * Some HATEOAS experts use synonym {@code "details"}.
     *
     * @see <a href="https://tools.ietf.org/html/rfc6903#section-2">Additional
     * Link Relation Types (RFC 6903), Section 2</a>
     */
    ABOUT("about"),

    /**
     * Indicates that the current document has an alternate representation (like
     * different format and/or language).
     *
     * @see <a href="http://www.w3.org/TR/html5/links.html#rel-alternate"> HTML
     * 5 Link type "alternate", Section 4.8.4.1</a>
     */
    ALTERNATE("alternate"),

    /**
     * Indicates that the referenced document serves as a table of contents.
     *
     * @see <a href="http://www.w3.org/TR/html4/types.html#h-6.12">HTML 4.01
     * Link types, Section 6.12</a>
     */
    CONTENTS("contents"),

    /**
     * Indicates that the main content of the current document is covered by the
     * copyright license described by the referenced document.
     *
     * @see <a href="http://www.w3.org/TR/html5/links.html#link-type-license">
     * HTML 5 Link type "license", Section 4.8.4.6</a>
     */
    LICENSE("license"),

    /**
     * Indicates that the current document is a part of a series, and that the
     * next document in the series is the referenced document.
     *
     * @see <a href="http://www.w3.org/TR/html5/links.html#link-type-next"> HTML
     * 5 Link type "next", Section 4.8.4.13.1</a>
     * @see #PREV
     */
    NEXT("next"),

    /**
     * Indicates that the current document is a part of a series, and that the
     * previous document in the series is the referenced document.
     *
     * @see <a href="http://www.w3.org/TR/html5/links.html#link-type-prev"> HTML
     * 5 Link type "prev", Section 4.8.4.13.2</a>
     * @see #NEXT
     * @see #START
     */
    PREV("prev"),

    /**
     * Indicates that the link identifies a resource equivalent to the current
     * document or containing element.
     *
     * @see <a href="https://tools.ietf.org/html/rfc4287#section-4.2.7">The Atom
     * Syndication Format (RFC 4287), Section 4.2.7.2 #3</a>
     */
    SELF("self"),

    /**
     * Indicates the first document in a collection of documents. This link type
     * tells search engines which document is considered by the author to be the
     * starting point of the collection.
     * <p>
     * RFC 5988 defines a synonym {@code "first"}.
     *
     * @see <a href="http://www.w3.org/TR/html4/types.html#h-6.12">HTML 4.01
     * Link types, Section 6.12</a>
     * @see #NEXT
     */
    START("start"),

    /**
     * TODO
     */
    UP("up");

    private String rel;

    private LinkRelation(String rel) {
        this.rel = rel;
    }

    /**
     * The literal value appropriate for describing the link relationship of a
     * hypermedia control. Use the value, for example, in the rendered
     * {@code rel} attribute of &lt;link&gt;.
     *
     * @return the relationship value
     */
    public String rel() {
        return rel;
    }

}