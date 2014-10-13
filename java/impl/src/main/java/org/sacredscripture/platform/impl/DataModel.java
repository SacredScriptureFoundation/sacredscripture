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
package org.sacredscripture.platform.impl;

/**
 * This class specifies constants about the relational data model.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
public final class DataModel {

    /**
     * @see org.sacredscripture.platform.impl.bible.BibleLocalizationImpl
     */
    public static final class BibleLocalizationTable {
        public static final String TABLE_NAME = "bible_loc";
        public static final String COLUMN_ABBREVIATION = "abbreviation";
        public static final String COLUMN_BIBLE_ID = "bible_id";
        public static final String COLUMN_COPYRIGHT = "copyright";
        public static final String COLUMN_LICENSE = "license";
        public static final String COLUMN_LOCALE = "locale_lang";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TITLE = "title";
    }

    /**
     * @see org.sacredscripture.platform.impl.bible.BibleImpl
     */
    public static final class BibleTable {
        public static final String TABLE_NAME = "bible";
        public static final String COLUMN_LOCALE = "locale_lang";
        public static final String COLUMN_RTOL = "rtol";
    }

    /**
     * @see org.sacredscripture.platform.impl.bible.BookImpl
     */
    public static final class BookTable {
        public static final String TABLE_NAME = "book";
        public static final String COLUMN_BIBLE_ID = "bible_id";
        public static final String COLUMN_BOOK_TYPE_ID = "book_type_id";
        public static final String COLUMN_CHAPTER_COUNT = "chapter_count";
        public static final String COLUMN_LIST_POSITION = "list_position";
    }

    /**
     * @see org.sacredscripture.platform.impl.bible.BookTypeGroupLocalizationImpl
     */
    public static final class BookTypeGroupLocalizationTable {
        public static final String TABLE_NAME = "book_type_group_loc";
        public static final String COLUMN_BOOK_GROUP_TYPE_ID = "book_group_type_id";
        public static final String COLUMN_LOCALE = "locale_lang";
        public static final String COLUMN_NAME = "name";
    }

    /**
     * @see org.sacredscripture.platform.impl.bible.BookTypeGroupImpl
     */
    public static final class BookTypeGroupTable {
        public static final String TABLE_NAME = "book_type_group";
        public static final String COLUMN_BOOK_GROUP_TYPE_ID = "book_group_type_id";
        public static final String COLUMN_LIST_POSITION = "list_position";
        public static final String COLUMN_PARENT_ID = "parent_id";
    }

    /**
     * @see org.sacredscripture.platform.impl.bible.BookTypeLocalizationImpl
     */
    public static final class BookTypeLocalizationTable {
        public static final String TABLE_NAME = "book_type_loc";
        public static final String COLUMN_ABBREVIATION1 = "abbreviation1";
        public static final String COLUMN_ABBREVIATION2 = "abbreviation2";
        public static final String COLUMN_ABBREVIATION3 = "abbreviation3";
        public static final String COLUMN_BOOK_TYPE_ID = "book_type_id";
        public static final String COLUMN_LOCALE = "locale_lang";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TITLE = "title";
    }

    /**
     * @see org.sacredscripture.platform.impl.bible.BookTypeImpl
     */
    public static final class BookTypeTable {
        public static final String TABLE_NAME = "book_type";
        public static final String COLUMN_BOOK_GROUP_TYPE_ID = "book_group_type_id";
        public static final String COLUMN_CODE = "type_code";
    }

    public static final String AUDIT_COLUMN_CREATED = "created";

    public static final String AUDIT_COLUMN_UPDATED = "updated";

}
