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
import org.sacredscripture.platform.impl.DataModel.ContentTable;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

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
    @OrderColumn(name = ContentTable.COLUMN_POSITION)
    private List<Verse> verses;

    @Column(name = ContentTable.COLUMN_CHAPTER_NAME)
    private String name;

    @Override
    public ContentKind getContentKind() {
        return ContentKind.CHAPTER;
    }

    @Override
    public String getName() {
        return name;
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

}
