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
package org.sacredscripture.platform.internal.bible.dao;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.sacredscripture.platform.internal.ObjectMother;
import org.sacredscripture.platform.internal.bible.BookTypeGroupImpl;
import org.sacredscripture.platform.internal.bible.BookTypeImpl;
import org.sacredscripture.platform.internal.dao.BookTypeDaoImpl;

import org.sacredscripturefoundation.commons.test.AbstractSpringJpaIntegrationTests;

import org.junit.Before;
import org.junit.Test;

/**
 * Persistence integeration tests for {@link BookTypeDaoImpl}.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
public class BookTypeDaoImplPersistenceITest extends AbstractSpringJpaIntegrationTests {

    private BookTypeDaoImpl dao;

    @Before
    public void onSetUp() {
        dao = new BookTypeDaoImpl();
        dao.setEntityManager(em);
    }

    /**
     * Verifies type can be found by its code.
     */
    @Test
    public void testFindCode() {
        BookTypeGroupImpl g = ObjectMother.newBookTypeGroup();
        BookTypeImpl t = ObjectMother.newBookType(g);
        em.persist(g);
        em.persist(t);
        assertSame(t, dao.findByCode(t.getCode()));
        // case insensitive checks
        assertSame(t, dao.findByCode(t.getCode().toUpperCase()));
        assertSame(t, dao.findByCode(t.getCode().toLowerCase()));
    }

    /**
     * Verifies type cannot be found by the wrong code.
     */
    @Test
    public void testFindCodeNotFound() {
        BookTypeGroupImpl g = ObjectMother.newBookTypeGroup();
        BookTypeImpl t = ObjectMother.newBookType(g);
        em.persist(g);
        em.persist(t);
        assertNull(dao.findByCode("z"));
    }

}
