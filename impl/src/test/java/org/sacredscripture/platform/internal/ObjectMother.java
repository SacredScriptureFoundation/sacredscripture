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
package org.sacredscripture.platform.internal;

import static org.sacredscripturefoundation.commons.test.TestUtils.randomText;

import org.sacredscripture.platform.bible.Bible;
import org.sacredscripture.platform.bible.canon.BookType;
import org.sacredscripture.platform.bible.canon.BookTypeGroup;
import org.sacredscripture.platform.internal.bible.BibleImpl;
import org.sacredscripture.platform.internal.bible.BibleLocalizationImpl;
import org.sacredscripture.platform.internal.bible.BookImpl;
import org.sacredscripture.platform.internal.bible.ChapterImpl;
import org.sacredscripture.platform.internal.bible.VerseImpl;
import org.sacredscripture.platform.internal.bible.VerseTextImpl;
import org.sacredscripture.platform.internal.bible.canon.BookTypeGroupImpl;
import org.sacredscripture.platform.internal.bible.canon.BookTypeGroupLocalizationImpl;
import org.sacredscripture.platform.internal.bible.canon.BookTypeImpl;
import org.sacredscripture.platform.internal.bible.canon.BookTypeLocalizationImpl;

import java.util.Locale;

/**
 * This class contains static factory methods to "birth" example entities that
 * can be used in complex testing scenarios.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
public final class ObjectMother {

    public static BibleImpl newBible() {
        BibleImpl b = new BibleImpl();
        b.setCode(randomText(6));
        b.setLocale(Locale.ENGLISH);
        b.setPublicId(randomText(22));
        b.setRightToLeftReading(true);
        b.setDefault(true);
        return b;
    }

    public static BibleLocalizationImpl newBibleLocalization(Bible b) {
        BibleLocalizationImpl loc = new BibleLocalizationImpl();
        loc.setAbbreviation(randomText());
        loc.setBible(b);
        loc.setCopyrightNotice(randomText());
        loc.setLicense(randomText());
        loc.setLocale(Locale.ENGLISH);
        loc.setName(randomText());
        loc.setTitle(randomText());
        return loc;
    }

    public static BookImpl newBook(Bible b, BookType t) {
        BookImpl book = new BookImpl();
        book.setBible(b);
        book.setBookType(t);
        return book;
    }

    public static BookTypeImpl newBookType(BookTypeGroup g) {
        BookTypeImpl t = new BookTypeImpl();
        t.setBookTypeGroup(g);
        t.setCode(randomText(3));
        return t;
    }

    public static BookTypeGroupImpl newBookTypeGroup() {
        BookTypeGroupImpl g = new BookTypeGroupImpl();
        g.setCode(randomText(3));
        return g;
    }

    public static BookTypeGroupLocalizationImpl newBookTypeGroupLocalization(BookTypeGroup g) {
        BookTypeGroupLocalizationImpl loc = new BookTypeGroupLocalizationImpl();
        loc.setBookTypeGroup(g);
        loc.setLocale(Locale.ENGLISH);
        loc.setName(randomText());
        return loc;
    }

    public static BookTypeLocalizationImpl newBookTypeLocalization(BookType t) {
        BookTypeLocalizationImpl loc = new BookTypeLocalizationImpl();
        for (int i = 1; i <= 4; i++) {
            loc.addAbbreviation(randomText());
        }
        loc.setBookType(t);
        loc.setLocale(Locale.ENGLISH);
        loc.setName(randomText());
        loc.setTitle(randomText());
        return loc;
    }

    public static ChapterImpl newChapter(BookImpl k) {
        ChapterImpl c = new ChapterImpl();
        c.setName(randomText());
        c.setPublicId(randomText(22));
        k.addContent(c);
        return c;
    }

    public static VerseImpl newVerse(ChapterImpl c) {
        VerseImpl v = new VerseImpl();
        v.setAltName(randomText());
        v.setBook(c.getBook());
        v.setCode(randomText());
        v.setName(randomText());
        v.setPublicId(randomText(22));
        c.getBook().addContent(v);
        c.addVerse(v);

        VerseTextImpl text = new VerseTextImpl();
        text.setText(randomText());
        v.setText(text);
        return v;
    }

    private ObjectMother() {
        throw new AssertionError();
    }

}
