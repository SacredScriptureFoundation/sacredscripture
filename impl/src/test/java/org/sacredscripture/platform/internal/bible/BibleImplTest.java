/*
 * Copyright (c) 2013, 2015 Sacred Scripture Foundation.
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.sacredscripturefoundation.commons.locale.LocaleContextHolder;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link BibleImpl}.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
public class BibleImplTest {

    private BibleImpl bible;

    private void addLocalization(Locale locale) {
        BibleLocalizationImpl loc = new BibleLocalizationImpl();
        loc.setLocale(locale);
        loc.setAbbreviation("abbr-" + locale.toLanguageTag());
        loc.setCopyrightNotice("copy-" + locale.toLanguageTag());
        loc.setLicense("lic-" + locale.toLanguageTag());
        loc.setName("name-" + locale.toLanguageTag());
        loc.setTitle("title-" + locale.toLanguageTag());
        bible.addLocalizedContent(loc);
    }

    @Before
    public void onSetUp() throws Exception {
        bible = new BibleImpl();
    }

    /**
     * Verifies adding a book appends it to the bible's book list, book receives
     * a back-reference to the bible, and book gets it's list position.
     */
    @Test
    public void testAddBook() {
        assertTrue(bible.getBooks().isEmpty());

        BookImpl book = new BookImpl();
        BookTypeImpl bt = new BookTypeImpl();
        bt.setOrder(0);
        book.setBookType(bt);
        bible.addBook(book);

        assertEquals(1, bible.getBooks().size());
        assertSame(book, bible.getBooks().get(0));
        assertSame(bible, book.getBible());
        assertEquals(0, book.getOrder());
    }

    @Test
    public void testAddBook2() {
        BookImpl b1 = new BookImpl();
        BookTypeImpl bt1 = new BookTypeImpl();
        bt1.setOrder(1);
        b1.setBookType(bt1);
        bible.addBook(b1);

        assertEquals(1, bible.getBooks().size());
        assertSame(b1, bible.getBooks().get(0));

        BookImpl b2 = new BookImpl();
        BookTypeImpl bt2 = new BookTypeImpl();
        bt2.setOrder(2);
        b2.setBookType(bt2);
        bible.addBook(b2);

        assertEquals(2, bible.getBooks().size());
        assertSame(b1, bible.getBooks().get(0));
        assertSame(b2, bible.getBooks().get(1));

        BookImpl b3 = new BookImpl();
        BookTypeImpl bt3 = new BookTypeImpl();
        bt3.setOrder(0);
        b3.setBookType(bt3);
        bible.addBook(b3);

        assertEquals(3, bible.getBooks().size());
        assertSame(b3, bible.getBooks().get(0));
        assertSame(b1, bible.getBooks().get(1));
        assertSame(b2, bible.getBooks().get(2));
    }

    /**
     * Verifies NPE adding a {@code null} book.
     */
    @Test(expected = NullPointerException.class)
    public void testAddBookNull() {
        bible.addBook(null);
    }

    @Test
    public void testAddLocalizedContent() {
        BibleLocalizationImpl loc = new BibleLocalizationImpl();
        loc.setLocale(Locale.ENGLISH);
        bible.addLocalizedContent(loc);
        assertSame(bible, loc.getBible());
    }

    @Test
    public void testGetAbbreviationLocaleFallback() {
        bible.setLocale(Locale.ENGLISH);
        addLocalization(Locale.ENGLISH);
        LocaleContextHolder.setLocale(Locale.GERMAN);
        assertEquals("abbr-en", bible.getAbbreviation());
    }

    @Test
    public void testGetAbbreviationLocaleMatch() {
        bible.setLocale(Locale.ENGLISH);
        addLocalization(Locale.ENGLISH);
        addLocalization(Locale.GERMAN);
        LocaleContextHolder.setLocale(Locale.GERMAN);
        assertEquals("abbr-de", bible.getAbbreviation());
    }

    @Test
    public void testGetBooksNeverNull() {
        assertNotNull(bible.getBooks());
    }

    @Test
    public void testGetCopyrightLocaleFallback() {
        bible.setLocale(Locale.ENGLISH);
        addLocalization(Locale.ENGLISH);
        LocaleContextHolder.setLocale(Locale.GERMAN);
        assertEquals("copy-en", bible.getCopyrightNotice());
    }

    @Test
    public void testGetCopyrightLocaleMatch() {
        bible.setLocale(Locale.ENGLISH);
        addLocalization(Locale.ENGLISH);
        addLocalization(Locale.GERMAN);
        LocaleContextHolder.setLocale(Locale.GERMAN);
        assertEquals("copy-de", bible.getCopyrightNotice());
    }

    @Test
    public void testGetLicenseLocaleFallback() {
        bible.setLocale(Locale.ENGLISH);
        addLocalization(Locale.ENGLISH);
        LocaleContextHolder.setLocale(Locale.GERMAN);
        assertEquals("lic-en", bible.getLicense());
    }

    @Test
    public void testGetLicenseLocaleMatch() {
        bible.setLocale(Locale.ENGLISH);
        addLocalization(Locale.ENGLISH);
        addLocalization(Locale.GERMAN);
        LocaleContextHolder.setLocale(Locale.GERMAN);
        assertEquals("lic-de", bible.getLicense());
    }

    @Test
    public void testGetLocale() {
        bible.setLocale(Locale.ENGLISH);
        assertEquals(Locale.ENGLISH, bible.getLocale());
    }

    @Test
    public void testGetNameLocaleFallback() {
        bible.setLocale(Locale.ENGLISH);
        addLocalization(Locale.ENGLISH);
        LocaleContextHolder.setLocale(Locale.GERMAN);
        assertEquals("name-en", bible.getName());
    }

    @Test
    public void testGetNameLocaleMatch() {
        bible.setLocale(Locale.ENGLISH);
        addLocalization(Locale.ENGLISH);
        addLocalization(Locale.GERMAN);
        LocaleContextHolder.setLocale(Locale.GERMAN);
        assertEquals("name-de", bible.getName());
    }

    @Test
    public void testGetTitleLocaleFallback() {
        bible.setLocale(Locale.ENGLISH);
        addLocalization(Locale.ENGLISH);
        LocaleContextHolder.setLocale(Locale.GERMAN);
        assertEquals("title-en", bible.getTitle());
    }

    @Test
    public void testGetTitleLocaleMatch() {
        bible.setLocale(Locale.ENGLISH);
        addLocalization(Locale.ENGLISH);
        addLocalization(Locale.GERMAN);
        LocaleContextHolder.setLocale(Locale.GERMAN);
        assertEquals("title-de", bible.getTitle());
    }

    @Test
    public void testIsRightToLeftReading() {
        bible.setRightToLeftReading(true);
        assertTrue(bible.isRightToLeftReading());
        bible.setRightToLeftReading(false);
        assertFalse(bible.isRightToLeftReading());
    }

    /**
     * Verifies the code is normalized to lowercase.
     */
    @Test
    public void testSetCodeNormalize() {
        bible.setCode("X");
        assertEquals("x", bible.getCode());
    }

    /**
     * Verifies a {@code null} code is accepted.
     */
    @Test
    public void testSetCodeNull() {
        bible.setCode(null);
        assertNull(bible.getCode());
    }

}
