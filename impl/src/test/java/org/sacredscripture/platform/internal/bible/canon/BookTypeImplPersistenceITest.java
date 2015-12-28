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
package org.sacredscripture.platform.internal.bible.canon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.sacredscripture.platform.internal.DataModel.AUDIT_COLUMN_CREATED;
import static org.sacredscripture.platform.internal.DataModel.AUDIT_COLUMN_UPDATED;
import static org.sacredscripture.platform.internal.DataModel.BookTypeTable.COLUMN_BOOK_TYPE_GROUP_ID;
import static org.sacredscripture.platform.internal.DataModel.BookTypeTable.COLUMN_CODE;
import static org.sacredscripture.platform.internal.DataModel.BookTypeTable.COLUMN_ID;
import static org.sacredscripture.platform.internal.DataModel.BookTypeTable.COLUMN_POSITION;

import org.sacredscripture.platform.internal.ObjectMother;
import org.sacredscripture.platform.internal.bible.canon.BookTypeGroupImpl;
import org.sacredscripture.platform.internal.bible.canon.BookTypeImpl;
import org.sacredscripture.platform.internal.bible.canon.BookTypeLocalizationImpl;

import org.sacredscripturefoundation.commons.test.AbstractSpringJpaIntegrationTests;

import org.junit.Test;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * Persistence integeration tests for {@link BookTypeImpl}.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
public class BookTypeImplPersistenceITest extends AbstractSpringJpaIntegrationTests {

    /**
     * Verifies row after entity insert.
     */
    @Test
    public void testInsert() {
        BookTypeGroupImpl g = ObjectMother.newBookTypeGroup();
        BookTypeImpl t = ObjectMother.newBookType(g);
        em.persist(g);
        em.persist(t);
        em.flush();

        SqlRowSet rs = jdbcTemplate.queryForRowSet("select * from book_type where id=?", t.getId());
        assertTrue(rs.next());
        assertNotNull(rs.getDate(AUDIT_COLUMN_CREATED));
        assertNotNull(rs.getDate(AUDIT_COLUMN_UPDATED));
        assertEquals(t.getId().longValue(), rs.getLong(COLUMN_ID));
        assertEquals(t.getBookTypeGroup().getId().longValue(), rs.getLong(COLUMN_BOOK_TYPE_GROUP_ID));
        assertEquals(t.getCode(), rs.getString(COLUMN_CODE));
        assertEquals(t.getOrder(), rs.getInt(COLUMN_POSITION));
    }

    /**
     * Verifies localization is persisted by cascade.
     */
    @Test
    public void testSaveCascadeLocalization() {
        BookTypeGroupImpl g = ObjectMother.newBookTypeGroup();
        BookTypeImpl t = ObjectMother.newBookType(g);
        BookTypeLocalizationImpl loc = ObjectMother.newBookTypeLocalization(t);
        t.addLocalizedContent(loc);
        assertTransient(loc);
        em.persist(g);
        em.persist(t);
        assertNotTransient(loc);
        em.flush();
    }

}
