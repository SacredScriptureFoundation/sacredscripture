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

import org.sacredscripture.platform.api.bible.BookType;
import org.sacredscripture.platform.api.bible.BookTypeGroup;
import org.sacredscripture.platform.api.bible.BookTypeLocalization;
import org.sacredscripture.platform.impl.DataModel.BookTypeTable;

import org.sacredscripturefoundation.commons.locale.entity.LocalizableEntity;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * This class is the stock implementation of {@link BookType}.
 *
 * @author Paul Benedict
 * @see BookTypeLocalizationImpl
 * @since Sacred Scripture Platform 1.0
 */
@Entity
@Table(name = BookTypeTable.TABLE_NAME)
public class BookTypeImpl extends LocalizableEntity<Long, BookTypeLocalization> implements BookType {

    @Column(name = BookTypeTable.COLUMN_CODE)
    private String code;

    @ManyToOne(targetEntity = BookTypeGroupImpl.class)
    @JoinColumn(name = BookTypeTable.COLUMN_BOOK_TYPE_GROUP_ID)
    private BookTypeGroup bookTypeGroup;

    // @OneToMany(targetEntity = BookTypeLocalizationImpl.class, mappedBy =
    // "bookType")
    // @MapKeyJoinColumn(name = BookTypeLocalizationTable.COLUMN_BOOK_TYPE_ID)
    // @MapKey(name = "locale")
    @Transient
    private Map<Locale, BookTypeLocalization> localizedContents;

    @Override
    public BookTypeGroup getBookTypeGroup() {
        return bookTypeGroup;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public Map<Locale, BookTypeLocalization> getLocalizedContents() {
        if (localizedContents == null) {
            localizedContents = new HashMap<>();
        }
        return localizedContents;
    }

    public void setBookTypeGroup(BookTypeGroup bookTypeGroup) {
        this.bookTypeGroup = bookTypeGroup;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
