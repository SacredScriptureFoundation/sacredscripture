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

import org.sacredscripturefoundation.commons.Named;
import org.sacredscripturefoundation.commons.entity.Entity;

import java.util.List;

/**
 * This interface defines a Bible verse.
 *
 * @author Paul Benedict
 * @version $Rev: 173 $
 * @see Chapter#getVerses()
 * @since 1.0
 */
public interface Verse extends Entity<Long>, Named {

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
     * @see #getName()
     */
    String getAltName();

    /**
     * Retrieves the owning chapter of this verse.
     *
     * @return the owning chapter
     */
    Chapter getChapter();

    /**
     * Retrieves the complex content of this verse.
     *
     * @return the content
     */
    List<VerseContent> getContent();

    /**
     * Retrieves the name of this verse.
     *
     * @return the name
     */
    @Override
    String getName();

    /**
     * Retrieves the order value of this verse, with a higher value meaning
     * greater in terms of sorting. The meaning of the order is defined by the
     * canonical positioning within its owning bible.
     *
     * @return the order value
     */
    int getOrder();

    /**
     * Retrieves the list of similar verses. A similar verse is one that is
     * conceptually identical in another Bible but may be in a different
     * language or book group.
     *
     * @return the type
     */
    List<Verse> getSimilarVerses();

    /**
     * Retrieves a flag indicating whether this verse should be ommited in this
     * bible translation. In some bible translations, there are verses omitted
     * for scholarly reasons (such as reasonable doubt that a verse was not part
     * of the scriptural autograph). In such instances, these verses should be
     * marked as omitted so that they will not be displayed to the user.
     *
     * @return {@code true} if omitted; otherwise {@code false}
     */
    boolean isOmitted();

}