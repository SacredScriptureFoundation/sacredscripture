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

import org.sacredscripturefoundation.commons.entity.Entity;

public interface VerseContent extends Entity<Long> {

    /**
     * Retrieves the order value of this content, with a higher value meaning
     * greater in terms of sorting.
     *
     * @return the order value
     */
    int getOrder();

    /**
     * Retrieves the type of content. This should be used to determine the
     * appropriate cast to a subinterface.
     *
     * @return the type
     */
    VerseContentType getType();

    /**
     * Retrieves the owning verse.
     *
     * @return the verse
     */
    Verse getVerse();

}
