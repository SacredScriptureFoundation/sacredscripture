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
import static org.junit.Assert.assertTrue;
import static org.sacredscripture.platform.internal.DataModel.AUDIT_COLUMN_CREATED;
import static org.sacredscripture.platform.internal.DataModel.AUDIT_COLUMN_UPDATED;
import static org.sacredscripture.platform.internal.DataModel.BibleLocalizationTable.COLUMN_ID;
import static org.sacredscripture.platform.internal.DataModel.BibleTable.COLUMN_CODE;
import static org.sacredscripture.platform.internal.DataModel.BibleTable.COLUMN_LOCALE;
import static org.sacredscripture.platform.internal.DataModel.BibleTable.COLUMN_RTOL;

import org.sacredscripture.platform.internal.ObjectMother;
import org.sacredscripture.platform.internal.bible.BibleImpl;
import org.sacredscripture.platform.internal.bible.BibleLocalizationImpl;

import org.sacredscripturefoundation.commons.test.AbstractSpringJpaIntegrationTests;

import java.util.Locale;

import org.junit.Test;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * Persistence integeration tests for {@link BibleImpl}.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
public class BibleImplPersistenceITest extends AbstractSpringJpaIntegrationTests {

    /**
     * Verifies row after entity insert.
     */
    @Test
    public void testInsert() {
        BibleImpl b = ObjectMother.newBible();
        em.persist(b);
        em.flush();

        SqlRowSet rs = jdbcTemplate.queryForRowSet("select * from bible where id=?", b.getId());
        assertTrue(rs.next());
        assertNotNull(rs.getDate(AUDIT_COLUMN_CREATED));
        assertNotNull(rs.getDate(AUDIT_COLUMN_UPDATED));
        assertEquals(b.getId().longValue(), rs.getLong(COLUMN_ID));
        assertEquals(b.getCode(), rs.getString(COLUMN_CODE));
        assertEquals(b.getLocale(), Locale.forLanguageTag(rs.getString(COLUMN_LOCALE)));
        assertEquals(b.isRightToLeftReading(), rs.getBoolean(COLUMN_RTOL));
    }

    /**
     * Verifies localization is persisted by cascade.
     */
    @Test
    public void testSaveCascadeLocalization() {
        BibleImpl b = ObjectMother.newBible();
        BibleLocalizationImpl loc = ObjectMother.newBibleLocalization(b);
        b.addLocalizedContent(loc);
        assertTransient(loc);
        em.persist(b);
        assertNotTransient(loc);
        em.flush();
    }

}
