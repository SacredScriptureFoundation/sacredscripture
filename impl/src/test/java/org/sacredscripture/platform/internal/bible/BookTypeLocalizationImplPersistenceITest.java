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
import static org.sacredscripture.platform.internal.DataModel.BookTypeLocalizationTable.COLUMN_ABBREVIATION1;
import static org.sacredscripture.platform.internal.DataModel.BookTypeLocalizationTable.COLUMN_ABBREVIATION2;
import static org.sacredscripture.platform.internal.DataModel.BookTypeLocalizationTable.COLUMN_ABBREVIATION3;
import static org.sacredscripture.platform.internal.DataModel.BookTypeLocalizationTable.COLUMN_ABBREVIATION4;
import static org.sacredscripture.platform.internal.DataModel.BookTypeLocalizationTable.COLUMN_BOOK_TYPE_ID;
import static org.sacredscripture.platform.internal.DataModel.BookTypeLocalizationTable.COLUMN_ID;
import static org.sacredscripture.platform.internal.DataModel.BookTypeLocalizationTable.COLUMN_LOCALE;
import static org.sacredscripture.platform.internal.DataModel.BookTypeLocalizationTable.COLUMN_NAME;
import static org.sacredscripture.platform.internal.DataModel.BookTypeLocalizationTable.COLUMN_TITLE;

import org.sacredscripture.platform.bible.BookTypeGroup;
import org.sacredscripture.platform.internal.ObjectMother;
import org.sacredscripture.platform.internal.bible.BookTypeImpl;
import org.sacredscripture.platform.internal.bible.BookTypeLocalizationImpl;

import org.sacredscripturefoundation.commons.test.AbstractSpringJpaIntegrationTests;

import java.util.Locale;

import org.junit.Test;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * Persistence integeration tests for {@link BookTypeLocalizationImpl}.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
public class BookTypeLocalizationImplPersistenceITest extends AbstractSpringJpaIntegrationTests {

    /**
     * Verifies row after entity insert.
     */
    @Test
    public void testInsert() {
        BookTypeGroup g = ObjectMother.newBookTypeGroup();
        BookTypeImpl t = ObjectMother.newBookType(g);
        BookTypeLocalizationImpl loc = ObjectMother.newBookTypeLocalization(t);
        em.persist(g);
        em.persist(t);
        em.persist(loc);
        em.flush();

        SqlRowSet rs = jdbcTemplate.queryForRowSet("select * from book_type_loc where id=?", loc.getId());
        assertTrue(rs.next());
        assertNotNull(rs.getDate(AUDIT_COLUMN_CREATED));
        assertNotNull(rs.getDate(AUDIT_COLUMN_UPDATED));
        assertEquals(loc.getId().longValue(), rs.getLong(COLUMN_ID));
        assertEquals(loc.getAbbreviations().get(0), rs.getString(COLUMN_ABBREVIATION1));
        assertEquals(loc.getAbbreviations().get(1), rs.getString(COLUMN_ABBREVIATION2));
        assertEquals(loc.getAbbreviations().get(2), rs.getString(COLUMN_ABBREVIATION3));
        assertEquals(loc.getAbbreviations().get(3), rs.getString(COLUMN_ABBREVIATION4));
        assertEquals(loc.getBookType().getId().longValue(), rs.getLong(COLUMN_BOOK_TYPE_ID));
        assertEquals(loc.getName(), rs.getString(COLUMN_NAME));
        assertEquals(loc.getLocale(), Locale.forLanguageTag(rs.getString(COLUMN_LOCALE)));
        assertEquals(loc.getTitle(), rs.getString(COLUMN_TITLE));
    }

}
