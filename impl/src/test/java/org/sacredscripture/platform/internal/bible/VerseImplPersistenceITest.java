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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.sacredscripture.platform.internal.DataModel.AUDIT_COLUMN_CREATED;
import static org.sacredscripture.platform.internal.DataModel.AUDIT_COLUMN_UPDATED;
import static org.sacredscripture.platform.internal.DataModel.BibleLocalizationTable.COLUMN_ID;
import static org.sacredscripture.platform.internal.DataModel.ContentTable.COLUMN_BOOK_ID;
import static org.sacredscripture.platform.internal.DataModel.ContentTable.COLUMN_CODE;
import static org.sacredscripture.platform.internal.DataModel.ContentTable.COLUMN_DISCRIMINATOR;
import static org.sacredscripture.platform.internal.DataModel.ContentTable.COLUMN_NEXT;
import static org.sacredscripture.platform.internal.DataModel.ContentTable.COLUMN_POSITION;
import static org.sacredscripture.platform.internal.DataModel.ContentTable.COLUMN_PREVIOUS;
import static org.sacredscripture.platform.internal.DataModel.ContentTable.COLUMN_PUBLIC_ID;
import static org.sacredscripture.platform.internal.DataModel.ContentTable.COLUMN_VERSE_ALT_NAME;
import static org.sacredscripture.platform.internal.DataModel.ContentTable.COLUMN_VERSE_CHAPTER_ID;
import static org.sacredscripture.platform.internal.DataModel.ContentTable.COLUMN_VERSE_NAME;
import static org.sacredscripture.platform.internal.DataModel.ContentTable.COLUMN_VERSE_OMIT;
import static org.sacredscripture.platform.internal.DataModel.ContentTable.COLUMN_VERSE_TEXT_ID;
import static org.sacredscripture.platform.internal.DataModel.ContentTable.DISCRIMINATOR_VERSE;

import org.sacredscripture.platform.internal.ObjectMother;
import org.sacredscripture.platform.internal.bible.canon.BookTypeGroupImpl;
import org.sacredscripture.platform.internal.bible.canon.BookTypeImpl;

import org.sacredscripturefoundation.commons.test.AbstractSpringJpaIntegrationTests;

import org.junit.Test;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * Persistence integeration tests for {@link VerseImpl}.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
public class VerseImplPersistenceITest extends AbstractSpringJpaIntegrationTests {

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
        VerseImpl vPrev = ObjectMother.newVerse(c);
        VerseImpl v = ObjectMother.newVerse(c);
        VerseImpl vNext = ObjectMother.newVerse(c);
        em.persist(g);
        em.persist(t);
        em.persist(b);
        em.persist(k);
        em.persist(c);
        em.persist(vPrev);
        em.persist(v);
        em.persist(vNext);
        em.flush();

        SqlRowSet rs = jdbcTemplate.queryForRowSet("select * from content where id=?", v.getId());
        assertTrue(rs.next());
        assertNotNull(rs.getDate(AUDIT_COLUMN_CREATED));
        assertNotNull(rs.getDate(AUDIT_COLUMN_UPDATED));
        assertEquals(v.getId().longValue(), rs.getLong(COLUMN_ID));
        assertEquals(v.getAltName(), rs.getString(COLUMN_VERSE_ALT_NAME));
        assertEquals(v.getBook().getId().longValue(), rs.getLong(COLUMN_BOOK_ID));
        assertEquals(v.getChapter().getId().longValue(), rs.getLong(COLUMN_VERSE_CHAPTER_ID));
        assertEquals(v.getCode(), rs.getString(COLUMN_CODE));
        assertEquals(v.getName(), rs.getString(COLUMN_VERSE_NAME));
        assertEquals(v.getNext().getId().longValue(), rs.getLong(COLUMN_NEXT));
        assertEquals(v.getOrder(), rs.getInt(COLUMN_POSITION));
        assertEquals(v.getPrevious().getId().longValue(), rs.getInt(COLUMN_PREVIOUS));
        assertEquals(v.getPublicId(), rs.getString(COLUMN_PUBLIC_ID));
        assertEquals(v.getText().getId().longValue(), rs.getLong(COLUMN_VERSE_TEXT_ID));
        assertEquals(v.isOmitted(), rs.getBoolean(COLUMN_VERSE_OMIT));
        assertEquals(DISCRIMINATOR_VERSE, rs.getString(COLUMN_DISCRIMINATOR));
        assertSame(v.getChapter().getBook(), v.getBook());
    }

}
