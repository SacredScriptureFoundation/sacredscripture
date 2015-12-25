/*
 * Copyright (c) 2014, 2015 Sacred Scripture Foundation.
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
package org.sacredscripture.platform.internal.bible;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.sacredscripture.platform.internal.DataModel.AUDIT_COLUMN_CREATED;
import static org.sacredscripture.platform.internal.DataModel.AUDIT_COLUMN_UPDATED;
import static org.sacredscripture.platform.internal.DataModel.BibleLocalizationTable.COLUMN_ID;
import static org.sacredscripture.platform.internal.DataModel.ContentTable.COLUMN_BOOK_ID;
import static org.sacredscripture.platform.internal.DataModel.ContentTable.COLUMN_CHAPTER_NAME;
import static org.sacredscripture.platform.internal.DataModel.ContentTable.COLUMN_DISCRIMINATOR;
import static org.sacredscripture.platform.internal.DataModel.ContentTable.COLUMN_POSITION;
import static org.sacredscripture.platform.internal.DataModel.ContentTable.COLUMN_PUBLIC_ID;
import static org.sacredscripture.platform.internal.DataModel.ContentTable.DISCRIMINATOR_CHAPTER;

import org.sacredscripture.platform.internal.ObjectMother;

import org.sacredscripturefoundation.commons.test.AbstractSpringJpaIntegrationTests;

import org.junit.Test;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * Persistence integeration tests for {@link ChapterImpl}.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
public class ChapterImplPersistenceITest extends AbstractSpringJpaIntegrationTests {

    /**
     * Verifies row after entity insert.
     */
    @Test
    public void testInsert() {
        BookTypeGroupImpl g = ObjectMother.newBookTypeGroup();
        BookTypeImpl t = ObjectMother.newBookType(g);
        BibleImpl b = ObjectMother.newBible();
        BookImpl k = ObjectMother.newBook(b, t);
        ChapterImpl c = ObjectMother.newChapter(k);
        em.persist(g);
        em.persist(t);
        em.persist(b);
        em.persist(k);
        em.persist(c);
        em.flush();

        SqlRowSet rs = jdbcTemplate.queryForRowSet("select * from content where id=?", c.getId());
        assertTrue(rs.next());
        assertNotNull(rs.getDate(AUDIT_COLUMN_CREATED));
        assertNotNull(rs.getDate(AUDIT_COLUMN_UPDATED));
        assertEquals(c.getId().longValue(), rs.getLong(COLUMN_ID));
        assertEquals(c.getBook().getId().longValue(), rs.getLong(COLUMN_BOOK_ID));
        assertEquals(c.getName(), rs.getString(COLUMN_CHAPTER_NAME));
        assertEquals(c.getOrder(), rs.getInt(COLUMN_POSITION));
        assertEquals(c.getPublicId(), rs.getString(COLUMN_PUBLIC_ID));
        assertEquals(DISCRIMINATOR_CHAPTER, rs.getString(COLUMN_DISCRIMINATOR));
    }

    /**
     * Verifies verse ordering isn't offset by how many items precede it.
     */
    @Test
    public void testVerseOrdering() {
        BookTypeGroupImpl g = ObjectMother.newBookTypeGroup();
        BookTypeImpl t = ObjectMother.newBookType(g);
        BibleImpl b = ObjectMother.newBible();
        BookImpl k = ObjectMother.newBook(b, t);
        ChapterImpl c1 = ObjectMother.newChapter(k);
        ChapterImpl c2 = ObjectMother.newChapter(k);
        VerseImpl v1 = ObjectMother.newVerse(c2);
        VerseImpl v2 = ObjectMother.newVerse(c2);
        em.persist(g);
        em.persist(t);
        em.persist(b);
        em.persist(k);
        em.persist(c1);
        em.persist(c2);
        em.persist(v1);
        em.persist(v2);
        em.flush();
        em.clear();

        ChapterImpl cc = em.find(ChapterImpl.class, c2.getId());
        assertEquals(2, cc.getVerses().size());
        assertEquals(v1.getId(), cc.getVerses().get(0).getId());
        assertEquals(v2.getId(), cc.getVerses().get(1).getId());
    }

}
