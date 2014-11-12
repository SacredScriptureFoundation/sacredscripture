/*
 * Copyright (c) 2014 Sacred Scripture Foundation.
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

import org.sacredscripture.platform.bible.VerseText;
import org.sacredscripture.platform.internal.DataModel.VerseTextTable;

import org.sacredscripturefoundation.commons.entity.EntityImpl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * This class is the stock implementation of {@link VerseText}.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
@Entity
@Table(name = VerseTextTable.TABLE_NAME)
public class VerseTextImpl extends EntityImpl<Long> implements VerseText {

    @Column(name = VerseTextTable.COLUMN_TEXT)
    private String text;

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

}
