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

import org.sacredscripture.platform.api.bible.Bible;
import org.sacredscripture.platform.api.bible.BibleLocalization;
import org.sacredscripture.platform.api.bible.Book;
import org.sacredscripture.platform.impl.DataModel.BibleLocalizationTable;
import org.sacredscripture.platform.impl.DataModel.BibleTable;

import org.sacredscripturefoundation.commons.locale.entity.LocaleLanguageConverter;
import org.sacredscripturefoundation.commons.locale.entity.LocalizableEntity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.MapKey;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * This class is the stock implementation of {@link Bible}.
 *
 * @author Paul Benedict
 * @see BibleLocalizationImpl
 * @since Sacred Scripture Platform 1.0
 */
@Entity
@Table(name = BibleTable.TABLE_NAME)
public class BibleImpl extends LocalizableEntity<Long, BibleLocalization> implements Bible {

    @Convert(converter = LocaleLanguageConverter.class)
    @Column(name = BibleTable.COLUMN_LOCALE)
    private Locale locale;

    @OneToMany(targetEntity = BibleLocalizationImpl.class, mappedBy = "bible", cascade = CascadeType.ALL)
    @MapKeyJoinColumn(name = BibleLocalizationTable.COLUMN_BIBLE_ID)
    @MapKey(name = "locale")
    Map<Locale, BibleLocalization> localizedContents;

    @Column(name = BibleTable.COLUMN_RTOL)
    private boolean rightToLeftReading;

    // @OneToMany(targetEntity = BookImpl.class, mappedBy = "bible")
    // @OrderColumn(name = BookTable.COLUMN_LIST_POSITION)
    @Transient
    private List<Book> books;

    @Override
    public void addBook(Book book) {
        Objects.requireNonNull(book);
        books.add(book);
        book.setBible(this);
    }

    @Override
    public String getAbbreviation() {
        return localize(getLocale()).getAbbreviation();
    }

    @Override
    public List<Book> getBooks() {
        if (books == null) {
            books = new LinkedList<>();
        }
        return books;
    }

    @Override
    public String getCopyrightNotice() {
        return localize(getLocale()).getCopyrightNotice();
    }

    @Override
    public String getLicense() {
        return localize(getLocale()).getLicense();
    }

    /**
     * @see #setLocale(Locale)
     */
    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public Map<Locale, BibleLocalization> getLocalizedContents() {
        if (localizedContents == null) {
            localizedContents = new HashMap<>();
        }
        return localizedContents;
    }

    @Override
    public String getName() {
        return localize(getLocale()).getName();
    }

    @Override
    public String getTitle() {
        return localize(getLocale()).getTitle();
    }

    @Override
    public boolean isRightToLeftReading() {
        return rightToLeftReading;
    }

    @Override
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public void setRightToLeftReading(boolean rightToLeftReading) {
        this.rightToLeftReading = rightToLeftReading;
    }

}
