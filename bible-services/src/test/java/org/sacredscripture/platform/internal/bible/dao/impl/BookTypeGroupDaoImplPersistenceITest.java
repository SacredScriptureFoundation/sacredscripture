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
package org.sacredscripture.platform.internal.bible.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.sacredscripture.platform.bible.canon.BookTypeGroup;
import org.sacredscripture.platform.internal.ObjectMother;
import org.sacredscripture.platform.internal.bible.canon.BookTypeGroupImpl;

import org.sacredscripturefoundation.commons.test.AbstractSpringJpaIntegrationTests;

import java.util.List;

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

    /**
     * Verifies only root groups are retrieved.
     */
    @Test
    public void testFindRoots() {
        BookTypeGroupImpl pg1 = ObjectMother.newBookTypeGroup();
        BookTypeGroupImpl cg1 = ObjectMother.newBookTypeGroup();
        pg1.addChild(cg1);

        BookTypeGroupImpl pg2 = ObjectMother.newBookTypeGroup();
        BookTypeGroupImpl cg2 = ObjectMother.newBookTypeGroup();
        pg2.addChild(cg2);

        em.persist(pg1);
        em.persist(cg1);
        em.persist(pg2);
        em.persist(cg2);

        List<BookTypeGroup> roots = dao.findRoots();
        assertEquals(2, roots.size());
        assertSame(pg1, roots.get(0));
        assertSame(pg2, roots.get(1));
    }

}
