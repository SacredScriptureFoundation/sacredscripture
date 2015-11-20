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
 * @see <a href="https://en.wikipedia.org/wiki/HATEOAS">HATEOAS on Wikipedia</a>
 */
public enum StandardLinkRelation {

    /**
     * TODO
     */
    ABOUT("about"),

    /**
     * Indicates that the current document has an alternate representation (like
     * different format or language).
     *
     * @see <a href="http://www.w3.org/TR/html5/links.html#rel-alternate"> HTML
     * 5 Link type "alternate", Section 4.8.4.1</a>
     */
    ALTERNATE("alternate"),

    /**
     * Table of contents.
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
     * TODO
     */
    FIRST("first"),

    /**
     * TODO
     */
    NEXT("next"),

    /**
     * TODO
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
     * TODO
     */
    UP("up");

    private String rel;

    private StandardLinkRelation(String rel) {
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