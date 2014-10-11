/*
 * Copyright (c) 2013, 2014 Sacred Scripture Foundation.
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
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link BibleLocalizationImpl}.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
public class BibleLocalizationImplTest {

    private BibleLocalizationImpl loc;

    @Before
    public void onSetUp() throws Exception {
        loc = new BibleLocalizationImpl();
    }

    @Test
    public void testGetAbbreviation() {
        loc.setAbbreviation("x");
        assertEquals("x", loc.getAbbreviation());
    }

    @Test
    public void testGetBible() {
        BibleImpl b = new BibleImpl();
        loc.setBible(b);
        assertSame(b, loc.getBible());
    }

    @Test
    public void testGetName() {
        loc.setName("x");
        assertEquals("x", loc.getName());
    }

    @Test
    public void testGetTitle() {
        loc.setTitle("x");
        assertEquals("x", loc.getTitle());
    }

}
