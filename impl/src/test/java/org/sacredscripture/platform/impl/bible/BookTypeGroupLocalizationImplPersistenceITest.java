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
import static org.sacredscripture.platform.impl.DataModel.BookTypeGroupLocalizationTable.COLUMN_BOOK_TYPE_GROUP_ID;
import static org.sacredscripture.platform.impl.DataModel.BookTypeGroupLocalizationTable.COLUMN_ID;
import static org.sacredscripture.platform.impl.DataModel.BookTypeGroupLocalizationTable.COLUMN_LOCALE;
import static org.sacredscripture.platform.impl.DataModel.BookTypeGroupLocalizationTable.COLUMN_NAME;

import org.sacredscripture.platform.bible.BookTypeGroup;
import org.sacredscripture.platform.impl.ObjectMother;

import org.sacredscripturefoundation.commons.test.AbstractSpringJpaIntegrationTests;

import java.util.Locale;

import org.junit.Test;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * Persistence integeration tests for {@link BookTypeGroupLocalizationImpl}.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
public class BookTypeGroupLocalizationImplPersistenceITest extends AbstractSpringJpaIntegrationTests {

    /**
     * Verifies row after entity insert.
     */
    @Test
    public void testInsert() {
        BookTypeGroup g = ObjectMother.newBookTypeGroup();
        BookTypeGroupLocalizationImpl loc = ObjectMother.newBookTypeGroupLocalization(g);
        em.persist(g);
        em.persist(loc);
        em.flush();

        SqlRowSet rs = jdbcTemplate.queryForRowSet("select * from book_type_group_loc where id=?", loc.getId());
        assertTrue(rs.next());
        assertNotNull(rs.getDate(AUDIT_COLUMN_CREATED));
        assertNotNull(rs.getDate(AUDIT_COLUMN_UPDATED));
        assertEquals(loc.getId().longValue(), rs.getLong(COLUMN_ID));
        assertEquals(loc.getBookTypeGroup().getId().longValue(), rs.getLong(COLUMN_BOOK_TYPE_GROUP_ID));
        assertEquals(loc.getLocale(), Locale.forLanguageTag(rs.getString(COLUMN_LOCALE)));
        assertEquals(loc.getName(), rs.getString(COLUMN_NAME));
    }

}
