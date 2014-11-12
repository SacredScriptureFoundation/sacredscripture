/*
 * Copyright (c) 2014 Sacred Scripture Foundation.
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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.sacredscripture.platform.internal.DataModel.AUDIT_COLUMN_CREATED;
import static org.sacredscripture.platform.internal.DataModel.AUDIT_COLUMN_UPDATED;
import static org.sacredscripture.platform.internal.DataModel.BookTypeGroupTable.COLUMN_CODE;
import static org.sacredscripture.platform.internal.DataModel.BookTypeGroupTable.COLUMN_ID;
import static org.sacredscripture.platform.internal.DataModel.BookTypeGroupTable.COLUMN_LIST_POSITION;
import static org.sacredscripture.platform.internal.DataModel.BookTypeGroupTable.COLUMN_PARENT_ID;

import org.sacredscripture.platform.internal.ObjectMother;
import org.sacredscripture.platform.internal.bible.BookTypeGroupImpl;
import org.sacredscripture.platform.internal.bible.BookTypeGroupLocalizationImpl;

import org.sacredscripturefoundation.commons.test.AbstractSpringJpaIntegrationTests;

import org.junit.Test;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * Persistence integeration tests for {@link BookTypeGroupImpl}.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
public class BookTypeGroupImplPersistenceITest extends AbstractSpringJpaIntegrationTests {

    /**
     * Verifies row of parent record insert.
     */
    @Test
    public void testInsert() {
        BookTypeGroupImpl g = ObjectMother.newBookTypeGroup();
        em.persist(g);
        em.flush();

        SqlRowSet rs = jdbcTemplate.queryForRowSet("select * from book_type_group where id=?", g.getId());
        assertTrue(rs.next());
        assertNotNull(rs.getDate(AUDIT_COLUMN_CREATED));
        assertNotNull(rs.getDate(AUDIT_COLUMN_UPDATED));
        assertEquals(g.getId().longValue(), rs.getLong(COLUMN_ID));
        assertEquals(g.getCode(), rs.getString(COLUMN_CODE));
        assertEquals(g.getOrder(), rs.getLong(COLUMN_LIST_POSITION));
        assertNull(rs.getObject(COLUMN_PARENT_ID));
    }

    /**
     * Verifies row of children record inserts.
     */
    @Test
    public void testInsertChildren() {
        BookTypeGroupImpl p = ObjectMother.newBookTypeGroup();
        BookTypeGroupImpl c1 = ObjectMother.newBookTypeGroup();
        BookTypeGroupImpl c2 = ObjectMother.newBookTypeGroup();
        p.addChild(c1);
        p.addChild(c2);
        em.persist(p);
        em.persist(c1);
        em.persist(c2);
        em.flush();

        SqlRowSet rs = jdbcTemplate.queryForRowSet("select * from book_type_group where id=?", c1.getId());
        assertTrue(rs.next());
        assertEquals(c1.getId().longValue(), rs.getLong(COLUMN_ID));
        assertEquals(c1.getParent().getId().longValue(), rs.getLong(COLUMN_PARENT_ID));
        assertEquals(0, rs.getLong(COLUMN_LIST_POSITION));

        rs = jdbcTemplate.queryForRowSet("select * from book_type_group where id=?", c2.getId());
        assertTrue(rs.next());
        assertEquals(c2.getId().longValue(), rs.getLong(COLUMN_ID));
        assertEquals(p.getId().longValue(), rs.getLong(COLUMN_PARENT_ID));
        assertEquals(1, rs.getLong(COLUMN_LIST_POSITION));
    }

    /**
     * Verifies localization is persisted by cascade.
     */
    @Test
    public void testSaveCascadeLocalization() {
        BookTypeGroupImpl g = ObjectMother.newBookTypeGroup();
        BookTypeGroupLocalizationImpl loc = ObjectMother.newBookTypeGroupLocalization(g);
        g.addLocalizedContent(loc);
        assertTransient(loc);
        em.persist(g);
        assertNotTransient(loc);
        em.flush();
    }

}
