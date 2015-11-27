/*
 * Copyright (c) 2013, 2015 Sacred Scripture Foundation.
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
package org.sacredscripture.platform.bible;

import org.sacredscripturefoundation.commons.Named;

import java.util.List;

/**
 * This interface defines a chapter within a bible.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
public interface Chapter extends Content, Named {

    /**
     * Adds the specified verse to this chapter.
     *
     * @param verse the verse
     * @throws NullPointerException if the verse is {@code null}
     * @see #getVerses()
     */
    void addVerse(Verse verse);

    /**
     * Retrieves the name of this chapter.
     *
     * @return the name
     * @see #setName(String)
     */
    @Override
    String getName();

    /**
     * Retrieves the reference to the proceeding chapter
     * <em>in the same book</em> as this chapter.
     *
     * @return the next chapter
     * @see #setPrevious(Chapter)
     * @see #getNext()
     */
    Chapter getNext();

    /**
     * Retrieves the reference to the preceding chapter
     * <em>in the same book</em> as this chapter.
     *
     * @return the previous verse
     * @see #setPrevious(Chapter)
     * @see #getNext()
     */
    Chapter getPrevious();

    /**
     * Retrieves the collection of verses that belong to this chapter. The
     * verses are sorted according to their content ordering.
     *
     * @return the verses
     * @see #addVerse(Verse)
     */
    List<Verse> getVerses();

    /**
     * Stores the new name for this chapter.
     *
     * @param name the name
     * @see #getName()
     */
    void setName(String name);

    /**
     * Stores a reference to the proceeding chapter <em>in the same book</em> as
     * this chapter.
     *
     * @param next the next chapter
     * @see #getNext()
     * @see #setPrevious(Chapter)
     */
    void setNext(Chapter next);

    /**
     * Stores a reference to the preceding chapter <em>in the same book</em> as
     * this chapter.
     *
     * @param previous the previous chapter
     * @see #getPrevious()
     * @see #setNext(Chapter)
     */
    void setPrevious(Chapter previous);

}