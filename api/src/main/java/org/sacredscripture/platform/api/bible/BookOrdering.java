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

/**
 * This enumeration defines the kinds of ordering a bible may sort its books.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
public enum BookOrdering {

    /**
     * Books are sorted by their canonical listing in their respective edition.
     */
    CANON,

    /**
     * Books are sorted alphabetically by their respective names.
     */
    NAME,

    /**
     * Books are sorted alphabetically by their respective names, but preceding
     * ordinals are ignored. A Book like "1 John" is less than "2 John", but
     * both are treated alphabetically like "John" (starting with J). However,
     * "John" is given the implicit ordinal of zero and is therefore greater
     * than both.
     */
    NAME_ORDINAL_INSENSITIVE;

}
