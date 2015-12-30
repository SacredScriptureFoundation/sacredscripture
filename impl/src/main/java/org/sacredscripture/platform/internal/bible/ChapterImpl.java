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

import org.sacredscripture.platform.bible.Chapter;
import org.sacredscripture.platform.bible.ContentKind;
import org.sacredscripture.platform.bible.Verse;
import org.sacredscripture.platform.internal.DataModel.ContentTable;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

/**
 * This class is the stock implementation of {@link Chapter}.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
@Entity
@DiscriminatorValue(ContentTable.DISCRIMINATOR_CHAPTER)
public class ChapterImpl extends ContentImpl implements Chapter {

    @OneToMany(targetEntity = VerseImpl.class, mappedBy = "chapter")
    @OrderBy("order")
    private List<Verse> verses;

    @Column(name = ContentTable.COLUMN_CHAPTER_NAME)
    private String name;

    @ManyToOne(targetEntity = ChapterImpl.class, fetch = FetchType.LAZY)
    @JoinColumn(name = ContentTable.COLUMN_CHAPTER_PREVIOUS_ID)
    private Chapter previous;

    @ManyToOne(targetEntity = ChapterImpl.class, fetch = FetchType.LAZY)
    @JoinColumn(name = ContentTable.COLUMN_CHAPTER_NEXT_ID)
    private Chapter next;

    @Override
    public void addVerse(Verse verse) {
        Objects.requireNonNull(verse);
        if (verses == null) {
            verses = new LinkedList<>();
        } else {
            Verse last = verses.get(verses.size() - 1);
            last.setNext(verse);
            verse.setPrevious(last);
        }
        verse.setChapter(this);
        verses.add(verse);
    }

    @Override
    public ContentKind getContentKind() {
        return ContentKind.CHAPTER;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Chapter getNext() {
        return next;
    }

    @Override
    public Chapter getPrevious() {
        return previous;
    }

    @Override
    public List<Verse> getVerses() {
        if (verses == null) {
            verses = new LinkedList<>();
        }
        return verses;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setNext(Chapter next) {
        this.next = next;
    }

    @Override
    public void setPrevious(Chapter previous) {
        this.previous = previous;
    }

}
