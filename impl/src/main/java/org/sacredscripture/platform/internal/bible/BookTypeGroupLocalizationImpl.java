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

import org.sacredscripture.platform.bible.canon.BookType;
import org.sacredscripture.platform.bible.canon.BookTypeGroup;
import org.sacredscripture.platform.bible.canon.BookTypeGroupLocalization;
import org.sacredscripture.platform.internal.DataModel.BookTypeGroupLocalizationTable;

import org.sacredscripturefoundation.commons.locale.entity.LocalizedContentEntity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * This class is the stock implementation of
 * {@link BookTypeGroupLocalizationImpl}.
 *
 * @author Paul Benedict
 * @see BookType
 * @since Sacred Scripture Platform 1.0
 */
@Entity
@Table(name = BookTypeGroupLocalizationTable.TABLE_NAME)
@AttributeOverride(name = "locale", column = @Column(name = BookTypeGroupLocalizationTable.COLUMN_LOCALE))
public class BookTypeGroupLocalizationImpl extends LocalizedContentEntity<Long> implements BookTypeGroupLocalization {

    @Column(name = BookTypeGroupLocalizationTable.COLUMN_NAME)
    private String name;

    @ManyToOne(targetEntity = BookTypeGroupImpl.class, optional = false)
    @JoinColumn(name = BookTypeGroupLocalizationTable.COLUMN_BOOK_TYPE_GROUP_ID)
    private BookTypeGroup bookTypeGroup;

    @Override
    public BookTypeGroup getBookTypeGroup() {
        return bookTypeGroup;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setBookTypeGroup(BookTypeGroup bookTypeGroup) {
        this.bookTypeGroup = bookTypeGroup;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

}
