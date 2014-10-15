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
import static org.junit.Assert.assertTrue;

import org.sacredscripture.platform.impl.DataModel.BibleTable;

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
     * Verifies row contents after entity insert.
     */
    @Test
    public void testInsert() {
        BibleImpl b = new BibleImpl();
        b.setLocale(Locale.ENGLISH);
        b.setRightToLeftReading(true);
        em.persist(b);
        em.flush();

        SqlRowSet rs = jdbcTemplate.queryForRowSet("select * from BIBLE where id=?", b.getId());
        assertTrue(rs.next());
        assertTrue(rs.getLong(BibleTable.COLUMN_ID) > 0);
        assertEquals(b.isRightToLeftReading(), rs.getBoolean(BibleTable.COLUMN_RTOL));
        assertEquals(b.getLocale(), Locale.forLanguageTag(rs.getString(BibleTable.COLUMN_LOCALE)));
    }

}
