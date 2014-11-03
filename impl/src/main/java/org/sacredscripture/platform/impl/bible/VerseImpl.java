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

import org.sacredscripture.platform.bible.Chapter;
import org.sacredscripture.platform.bible.ContentKind;
import org.sacredscripture.platform.bible.Verse;
import org.sacredscripture.platform.bible.VerseText;
import org.sacredscripture.platform.impl.DataModel.ContentTable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * This class is the stock implementation of {@link Verse}.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
@Entity
@DiscriminatorValue(ContentTable.DISCRIMINATOR_VERSE)
public class VerseImpl extends ContentImpl implements Verse {

    @Column(name = ContentTable.COLUMN_VERSE_NAME, nullable = false)
    private String name;

    @Column(name = ContentTable.COLUMN_VERSE_ALT_NAME)
    private String altName;

    @Column(name = ContentTable.COLUMN_VERSE_OMIT)
    private boolean omitted;

    @ManyToOne(targetEntity = ChapterImpl.class, optional = false)
    @JoinColumn(name = ContentTable.COLUMN_VERSE_CHAPTER_ID)
    private Chapter chapter;

    @OneToOne(targetEntity = VerseTextImpl.class, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = ContentTable.COLUMN_VERSE_TEXT_ID)
    private VerseText text;

    @Override
    public String getAltName() {
        return altName;
    }

    @Override
    public Chapter getChapter() {
        return chapter;
    }

    @Override
    public ContentKind getContentKind() {
        return ContentKind.VERSE;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public VerseText getText() {
        return text;
    }

    @Override
    public boolean isOmitted() {
        return omitted;
    }

    @Override
    public void setAltName(String altName) {
        this.altName = altName;
    }

    @Override
    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setOmitted(boolean omitted) {
        this.omitted = omitted;
    }

    @Override
    public void setText(VerseText text) {
        this.text = text;
    }

}
