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
package org.sacredscripture.platform.internal.bible;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.sacredscripture.platform.internal.bible.BibleImpl;
import org.sacredscripture.platform.internal.bible.BibleLocalizationImpl;
import org.sacredscripture.platform.internal.bible.BookImpl;

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

    @Test
    public void testAddBook() {
        assertTrue(bible.getBooks().isEmpty());
        BookImpl book = new BookImpl();
        bible.addBook(book);
        assertSame(book, bible.getBooks().get(0));
        assertSame(bible, book.getBible());
    }

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

}
