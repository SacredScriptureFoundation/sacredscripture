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
import static org.sacredscripture.platform.internal.DataModel.BookTable.COLUMN_BIBLE_ID;
import static org.sacredscripture.platform.internal.DataModel.BookTable.COLUMN_BOOK_TYPE_ID;
import static org.sacredscripture.platform.internal.DataModel.BookTable.COLUMN_ID;

import org.sacredscripture.platform.internal.ObjectMother;

import org.sacredscripturefoundation.commons.test.AbstractSpringJpaIntegrationTests;

import org.junit.Test;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * Persistence integeration tests for {@link BookImpl}.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
public class BookImplPersistenceITest extends AbstractSpringJpaIntegrationTests {

    /**
     * Verifies chapters are appropriately materialized.
     */
    @Test
    public void testGetChapters() {
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
        em.clear();

        assertEquals(1, k.getChapters().size());
        assertEquals(c.getId(), k.getChapters().get(0).getId());
    }

    /**
     * Verifies row after entity insert.
     */
    @Test
    public void testInsert() {
        BookTypeGroupImpl g = ObjectMother.newBookTypeGroup();
        BookTypeImpl t = ObjectMother.newBookType(g);
        BibleImpl b = ObjectMother.newBible();
        BookImpl k = ObjectMother.newBook(b, t);
        em.persist(g);
        em.persist(t);
        em.persist(b);
        em.persist(k);
        em.flush();

        SqlRowSet rs = jdbcTemplate.queryForRowSet("select * from book where id=?", k.getId());
        assertTrue(rs.next());
        assertNotNull(rs.getDate(AUDIT_COLUMN_CREATED));
        assertNotNull(rs.getDate(AUDIT_COLUMN_UPDATED));
        assertEquals(k.getId().longValue(), rs.getLong(COLUMN_ID));
        assertEquals(k.getBible().getId().longValue(), rs.getLong(COLUMN_BIBLE_ID));
        assertEquals(k.getBookType().getId().longValue(), rs.getLong(COLUMN_BOOK_TYPE_ID));
    }

}
