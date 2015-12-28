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
import org.sacredscripture.platform.bible.canon.BookTypeLocalization;
import org.sacredscripture.platform.internal.DataModel.BookTypeLocalizationTable;

import org.sacredscripturefoundation.commons.locale.entity.LocalizedContentEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * This class is the stock implementation of {@link BookTypeLocalization}.
 *
 * @author Paul Benedict
 * @see BookType
 * @since Sacred Scripture Platform 1.0
 */
@Entity
@Table(name = BookTypeLocalizationTable.TABLE_NAME)
@AttributeOverride(name = "locale", column = @Column(name = BookTypeLocalizationTable.COLUMN_LOCALE) )
public class BookTypeLocalizationImpl extends LocalizedContentEntity<Long> implements BookTypeLocalization {

    @ManyToOne(targetEntity = BookTypeImpl.class, optional = false)
    @JoinColumn(name = BookTypeLocalizationTable.COLUMN_BOOK_TYPE_ID)
    private BookType bookType;

    @Column(name = BookTypeLocalizationTable.COLUMN_NAME)
    private String name;

    @Column(name = BookTypeLocalizationTable.COLUMN_TITLE)
    private String title;

    @Column(name = BookTypeLocalizationTable.COLUMN_ABBREVIATION1)
    private String abbreviation1;

    @Column(name = BookTypeLocalizationTable.COLUMN_ABBREVIATION2)
    private String abbreviation2;

    @Column(name = BookTypeLocalizationTable.COLUMN_ABBREVIATION3)
    private String abbreviation3;

    @Column(name = BookTypeLocalizationTable.COLUMN_ABBREVIATION4)
    private String abbreviation4;

    @Transient
    private List<String> abbreviations;

    @Override
    public void addAbbreviation(String abbreviation) {
        if (abbreviation1 == null) {
            abbreviation1 = abbreviation;
        } else if (abbreviation2 == null) {
            abbreviation2 = abbreviation;
        } else if (abbreviation3 == null) {
            abbreviation3 = abbreviation;
        } else if (abbreviation4 == null) {
            abbreviation4 = abbreviation;
        } else {
            throw new IllegalArgumentException("Only 4 abbreviations supported");
        }
        rebuildAbbreviationList();
    }

    @Override
    public List<String> getAbbreviations() {
        if (abbreviations == null) {
            rebuildAbbreviationList();
        }
        return abbreviations;
    }

    @Override
    public BookType getBookType() {
        return bookType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTitle() {
        return title;
    }

    private void rebuildAbbreviationList() {
        List<String> list = new ArrayList<>(4);
        list.add(abbreviation1);
        if (abbreviation2 != null) {
            list.add(abbreviation2);
        }
        if (abbreviation3 != null) {
            list.add(abbreviation3);
        }
        if (abbreviation4 != null) {
            list.add(abbreviation4);
        }
        abbreviations = Collections.unmodifiableList(list);
    }

    @Override
    public void setAbbreviations(List<String> abbreviations) {
        abbreviation1 = null;
        abbreviation2 = null;
        abbreviation3 = null;
        abbreviation4 = null;
        if (abbreviations != null) {
            for (String abbr : abbreviations) {
                addAbbreviation(abbr);
            }
        } else {
            this.abbreviations = null;
        }
    }

    @Override
    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

}
