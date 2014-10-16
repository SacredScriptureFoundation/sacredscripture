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
package org.sacredscripture.platform.impl.bible;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.sacredscripture.platform.impl.DataModel.AUDIT_COLUMN_CREATED;
import static org.sacredscripture.platform.impl.DataModel.AUDIT_COLUMN_UPDATED;
import static org.sacredscripture.platform.impl.DataModel.BibleLocalizationTable.COLUMN_ABBREVIATION;
import static org.sacredscripture.platform.impl.DataModel.BibleLocalizationTable.COLUMN_BIBLE_ID;
import static org.sacredscripture.platform.impl.DataModel.BibleLocalizationTable.COLUMN_COPYRIGHT;
import static org.sacredscripture.platform.impl.DataModel.BibleLocalizationTable.COLUMN_ID;
import static org.sacredscripture.platform.impl.DataModel.BibleLocalizationTable.COLUMN_LICENSE;
import static org.sacredscripture.platform.impl.DataModel.BibleLocalizationTable.COLUMN_LOCALE;
import static org.sacredscripture.platform.impl.DataModel.BibleLocalizationTable.COLUMN_NAME;
import static org.sacredscripture.platform.impl.DataModel.BibleLocalizationTable.COLUMN_TITLE;

import org.sacredscripture.platform.impl.ObjectMother;

import org.sacredscripturefoundation.commons.test.AbstractSpringJpaIntegrationTests;

import java.util.Locale;

import org.junit.Test;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * Persistence integeration tests for {@link BibleLocalizationImpl}.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
public class BibleLocalizationImplPersistenceITest extends AbstractSpringJpaIntegrationTests {

    /**
     * Verifies row contents after entity insert.
     */
    @Test
    public void testInsert() {
        BibleImpl b = ObjectMother.newBible();
        BibleLocalizationImpl loc = ObjectMother.newBibleLocalization(b);
        em.persist(b);
        em.persist(loc);
        em.flush();

        SqlRowSet rs = jdbcTemplate.queryForRowSet("select * from bible_loc where id=?", loc.getId());
        assertTrue(rs.next());
        assertNotNull(rs.getDate(AUDIT_COLUMN_CREATED));
        assertNotNull(rs.getDate(AUDIT_COLUMN_UPDATED));
        assertEquals(loc.getId().longValue(), rs.getLong(COLUMN_ID));
        assertEquals(loc.getAbbreviation(), rs.getString(COLUMN_ABBREVIATION));
        assertEquals(loc.getBible().getId().longValue(), rs.getLong(COLUMN_BIBLE_ID));
        assertEquals(loc.getCopyrightNotice(), rs.getString(COLUMN_COPYRIGHT));
        assertEquals(loc.getLicense(), rs.getString(COLUMN_LICENSE));
        assertEquals(loc.getLocale(), Locale.forLanguageTag(rs.getString(COLUMN_LOCALE)));
        assertEquals(loc.getName(), rs.getString(COLUMN_NAME));
        assertEquals(loc.getTitle(), rs.getString(COLUMN_TITLE));
    }

}
