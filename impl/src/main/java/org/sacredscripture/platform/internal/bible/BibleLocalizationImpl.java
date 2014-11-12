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

import org.sacredscripture.platform.bible.Bible;
import org.sacredscripture.platform.bible.BibleLocalization;
import org.sacredscripture.platform.internal.DataModel.BibleLocalizationTable;

import org.sacredscripturefoundation.commons.locale.entity.LocalizedContentEntity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * This class is the stock implementation of {@link BibleLocalization}.
 *
 * @author Paul Benedict
 * @see BibleImpl
 * @since Sacred Scripture Platform 1.0
 */
@Entity
@Table(name = BibleLocalizationTable.TABLE_NAME)
@AttributeOverride(name = "locale", column = @Column(name = BibleLocalizationTable.COLUMN_LOCALE))
public class BibleLocalizationImpl extends LocalizedContentEntity<Long> implements BibleLocalization {

    @ManyToOne(targetEntity = BibleImpl.class)
    @JoinColumn(name = BibleLocalizationTable.COLUMN_BIBLE_ID)
    private Bible bible;

    @Column(name = BibleLocalizationTable.COLUMN_COPYRIGHT)
    private String copyrightNotice;

    @Column(name = BibleLocalizationTable.COLUMN_LICENSE)
    private String license;

    @Column(name = BibleLocalizationTable.COLUMN_NAME)
    private String name;

    @Column(name = BibleLocalizationTable.COLUMN_ABBREVIATION)
    private String abbreviation;

    @Column(name = BibleLocalizationTable.COLUMN_TITLE)
    private String title;

    @Override
    public String getAbbreviation() {
        return abbreviation;
    }

    @Override
    public Bible getBible() {
        return bible;
    }

    @Override
    public String getCopyrightNotice() {
        return copyrightNotice;
    }

    @Override
    public String getLicense() {
        return license;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @Override
    public void setBible(Bible bible) {
        this.bible = bible;
    }

    @Override
    public void setCopyrightNotice(String copyrightNotice) {
        this.copyrightNotice = copyrightNotice;
    }

    @Override
    public void setLicense(String license) {
        this.license = license;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return super.toString() + ",name=" + name;
    }

}
