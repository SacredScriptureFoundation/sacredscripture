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
package org.sacredscripture.platform.impl;

import static org.sacredscripturefoundation.commons.test.TestUtils.randomText;

import org.sacredscripture.platform.api.bible.Bible;
import org.sacredscripture.platform.impl.bible.BibleImpl;
import org.sacredscripture.platform.impl.bible.BibleLocalizationImpl;
import org.sacredscripture.platform.impl.bible.BookTypeGroupImpl;
import org.sacredscripture.platform.impl.bible.BookTypeImpl;

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
        b.setLocale(Locale.ENGLISH);
        b.setRightToLeftReading(true);
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

    public static BookTypeGroupImpl newBookTypeGroup() {
        BookTypeGroupImpl g = new BookTypeGroupImpl();
        return g;
    }

    public static BookTypeImpl newBookTypeImpl() {
        BookTypeImpl t = new BookTypeImpl();
        t.setCode(randomText(3));
        return t;
    }

    private ObjectMother() {
        throw new AssertionError();
    }

}
