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
package org.sacredscripture.platform.bible;

import org.sacredscripturefoundation.commons.Named;

/**
 * This interface defines a verse within a chapter.
 *
 * @author Paul Benedict
 * @see Chapter#getVerses()
 * @see VerseText
 * @since Sacred Scripture Platform 1.0
 */
public interface Verse extends Content, Named {

    /**
     * Retrieves the alternate textual name of this verse. This value is used
     * for historically abnormal naming, which are most likely found within the
     * deuterocanonical books, such as Additions to Esther (chapters A through
     * F) and Additions to Daniel.
     * <p>
     * For example, given that Esth 11:2 exists in Section A of Additions to
     * Esther, it is possible for the verse reference to be displayed as "Esth
     * 11:2 [A:1]" or "Esth A:1 [11:2]" depending on the construct.
     *
     * @return the alternate name
     * @see #setAltName(String)
     * @see #getName()
     */
    String getAltName();

    /**
     * Retrieves the owning chapter of this verse.
     *
     * @return the owning chapter
     * @see #setChapter(Chapter)
     */
    Chapter getChapter();

    /**
     * Retrieves the name of this verse. Do not presume the name is a number,
     * although in most cases it is. This name can be concatenated with the
     * chapter's name and book's name to present a marker to the user (e.g.,
     * "John 3:16").
     *
     * @return the name
     * @see #setName(String)
     * @see #getAltName()
     */
    @Override
    String getName();

    VerseText getText();

    /**
     * Retrieves a flag indicating whether this verse should be ommited in this
     * bible translation. In some bible translations, there are verses omitted
     * for scholarly reasons (such as reasonable doubt that a verse was not part
     * of the scriptural autograph). In such instances, these verses should be
     * marked as omitted so that they will not be displayed to the user.
     *
     * @return {@code true} if omitted; otherwise {@code false}
     * @see #setOmitted(boolean)
     */
    boolean isOmitted();

    /**
     * Stores the new alternate name for this verse.
     *
     * @param altName the alternate name
     * @see #getAltName()
     */
    void setAltName(String altName);

    /**
     * Stores the new owning chapter for this verse.
     *
     * @param chapter the new chapter
     * @see #getChapter()
     */
    void setChapter(Chapter chapter);

    /**
     * Stores the name of this verse.
     *
     * @param name the name
     * @see #getName()
     */
    void setName(String name);

    /**
     * Stores whether this verse should be omitted.
     *
     * @param omitted {@code true} to omit; otherwise {@code false}
     * @see #isOmitted()
     */
    void setOmitted(boolean omitted);

    void setText(VerseText text);

}