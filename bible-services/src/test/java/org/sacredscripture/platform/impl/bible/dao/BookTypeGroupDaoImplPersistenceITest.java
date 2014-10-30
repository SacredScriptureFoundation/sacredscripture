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
package org.sacredscripture.platform.impl.bible.dao;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.sacredscripture.platform.impl.ObjectMother;
import org.sacredscripture.platform.impl.bible.BookTypeGroupImpl;

import org.sacredscripturefoundation.commons.test.AbstractSpringJpaIntegrationTests;

import org.junit.Before;
import org.junit.Test;

/**
 * Persistence integeration tests for {@link BookTypeGroupDaoImpl}.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
public class BookTypeGroupDaoImplPersistenceITest extends AbstractSpringJpaIntegrationTests {

    private BookTypeGroupDaoImpl dao;

    @Before
    public void onSetUp() {
        dao = new BookTypeGroupDaoImpl();
        dao.setEntityManager(em);
    }

    /**
     * Verifies group can be found by its code.
     */
    @Test
    public void testFindCode() {
        BookTypeGroupImpl g = ObjectMother.newBookTypeGroup();
        em.persist(g);
        assertSame(g, dao.findByCode(g.getCode()));
        // case insensitive checks
        assertSame(g, dao.findByCode(g.getCode().toUpperCase()));
        assertSame(g, dao.findByCode(g.getCode().toLowerCase()));
    }

    /**
     * Verifies group cannot be found by the wrong code.
     */
    @Test
    public void testFindCodeNotFound() {
        BookTypeGroupImpl g = ObjectMother.newBookTypeGroup();
        em.persist(g);
        assertNull(dao.findByCode("z"));
    }

}
