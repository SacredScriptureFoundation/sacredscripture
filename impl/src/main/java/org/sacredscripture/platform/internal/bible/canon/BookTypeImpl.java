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
package org.sacredscripture.platform.internal.bible.canon;

import org.sacredscripture.platform.bible.canon.BookType;
import org.sacredscripture.platform.bible.canon.BookTypeGroup;
import org.sacredscripture.platform.bible.canon.BookTypeLocalization;
import org.sacredscripture.platform.internal.DataModel.BookTypeLocalizationTable;
import org.sacredscripture.platform.internal.DataModel.BookTypeTable;

import org.sacredscripturefoundation.commons.locale.entity.LocalizableEntity;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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

    @Column(name = BookTypeTable.COLUMN_POSITION)
    private int order;

    @ManyToOne(targetEntity = BookTypeGroupImpl.class)
    @JoinColumn(name = BookTypeTable.COLUMN_BOOK_TYPE_GROUP_ID)
    private BookTypeGroup bookTypeGroup;

    @OneToMany(targetEntity = BookTypeLocalizationImpl.class, mappedBy = "bookType", cascade = CascadeType.ALL)
    @MapKeyJoinColumn(name = BookTypeLocalizationTable.COLUMN_BOOK_TYPE_ID)
    @MapKey(name = "locale")
    private Map<Locale, BookTypeLocalization> localizedContents;

    @Override
    public void addLocalizedContent(BookTypeLocalization content) {
        super.addLocalizedContent(content);
        content.setBookType(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BookType)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        return Objects.equals(code, ((BookType) obj).getCode());
    }

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

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    @Override
    public void setBookTypeGroup(BookTypeGroup bookTypeGroup) {
        this.bookTypeGroup = bookTypeGroup;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public void setOrder(int order) {
        if (order < 0) {
            throw new IllegalArgumentException("Order cannot be negative");
        }
        this.order = order;
    }

}
