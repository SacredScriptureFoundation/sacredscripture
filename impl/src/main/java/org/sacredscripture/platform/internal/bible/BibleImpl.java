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

import org.sacredscripture.platform.bible.Bible;
import org.sacredscripture.platform.bible.BibleLocalization;
import org.sacredscripture.platform.bible.Book;
import org.sacredscripture.platform.internal.DataModel.BibleLocalizationTable;
import org.sacredscripture.platform.internal.DataModel.BibleTable;

import org.sacredscripturefoundation.commons.locale.LocaleContextHolder;
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
import javax.persistence.OrderBy;
import javax.persistence.Table;

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

    @Column(name = BibleTable.COLUMN_PUBLIC_ID)
    private String publicId;

    @Column(name = BibleTable.COLUMN_CODE)
    private String code;

    @Convert(converter = LocaleLanguageConverter.class)
    @Column(name = BibleTable.COLUMN_LOCALE)
    private Locale locale;

    @OneToMany(targetEntity = BibleLocalizationImpl.class, mappedBy = "bible", cascade = CascadeType.ALL)
    @MapKeyJoinColumn(name = BibleLocalizationTable.COLUMN_BIBLE_ID)
    @MapKey(name = "locale")
    Map<Locale, BibleLocalization> localizedContents;

    @Column(name = BibleTable.COLUMN_RTOL)
    private boolean rightToLeftReading;

    @Column(name = BibleTable.COLUMN_DEFAULT)
    private boolean defaultFlag;

    @OneToMany(targetEntity = BookImpl.class, mappedBy = "bible")
    @OrderBy("order")
    private List<Book> books;

    @Override
    public void addBook(Book book) {
        Objects.requireNonNull(book);
        book.setBible(this);

        int size = getBooks().size();
        int i;
        for (i = 0; i < size; i++) {
            if (getBooks().get(i).getBookType().getOrder() > book.getBookType().getOrder()) {
                break;
            }
        }

        getBooks().add(i, book);
        book.setOrder(i);

        for (i = i + 1; i < size + 1; i++) {
            getBooks().get(i).setOrder(i);
        }
    }

    @Override
    public void addLocalizedContent(BibleLocalization content) {
        content.setBible(this);
        super.addLocalizedContent(content);
    }

    @Override
    public String getAbbreviation() {
        return localize().getAbbreviation();
    }

    @Override
    public Book getBook(String bookCode) {
        for (Book book : getBooks()) {
            if (bookCode.equalsIgnoreCase(book.getBookType().getCode())) {
                return book;
            }
        }
        return null;
    }

    @Override
    public List<Book> getBooks() {
        if (books == null) {
            books = new LinkedList<>();
        }
        return books;
    }

    @Override
    public final String getCode() {
        return code;
    }

    @Override
    public String getCopyrightNotice() {
        return localize().getCopyrightNotice();
    }

    @Override
    public String getLicense() {
        return localize().getLicense();
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
        return localize().getName();
    }

    @Override
    public String getPublicId() {
        return publicId;
    }

    @Override
    public String getTitle() {
        return localize().getTitle();
    }

    @Override
    public final boolean isDefault() {
        return defaultFlag;
    }

    @Override
    public boolean isRightToLeftReading() {
        return rightToLeftReading;
    }

    /**
     * Determines the localization of this this bible according to the user's
     * current locale. If no such localization exists, always fallback to the
     * bible's native locale which is guaranteed to have a localization.
     *
     * @return the localization of this bible
     */
    private BibleLocalization localize() {
        BibleLocalization loc = null;
        Locale userLocale = LocaleContextHolder.getLocale();
        if (userLocale != null) {
            loc = localize(userLocale);
        }
        if (loc == null) {
            loc = Objects.requireNonNull(localize(getLocale()));
        }
        return loc;
    }

    @Override
    public final void setCode(String code) {
        this.code = (code != null ? code.toLowerCase() : null);
    }

    @Override
    public final void setDefault(boolean defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    @Override
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    @Override
    public void setRightToLeftReading(boolean rightToLeftReading) {
        this.rightToLeftReading = rightToLeftReading;
    }

}
