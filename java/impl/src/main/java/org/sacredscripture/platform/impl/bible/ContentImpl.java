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

import org.sacredscripture.platform.api.bible.Book;
import org.sacredscripture.platform.api.bible.Content;
import org.sacredscripture.platform.api.bible.ContentType;
import org.sacredscripture.platform.impl.DataModel.ContentTable;

import org.sacredscripturefoundation.commons.entity.EntityImpl;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * This abstract class is the stock implementation of {@link Content}.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
@Entity
@Table(name = ContentTable.TABLE_NAME)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = ContentTable.COLUMN_DISCRIMINATOR, discriminatorType = DiscriminatorType.INTEGER)
public abstract class ContentImpl extends EntityImpl<Long> implements Content {

    @ManyToOne(targetEntity = BookImpl.class, optional = false)
    @JoinColumn(name = ContentTable.COLUMN_BOOK_ID)
    private Book book;

    @Column(name = ContentTable.COLUMN_POSITION)
    private int order;

    @ManyToOne(targetEntity = ContentTypeImpl.class)
    @JoinColumn(name = ContentTable.COLUMN_CONTENT_TYPE_ID)
    private ContentType contentType;

    @Override
    public Book getBook() {
        return book;
    }

    @Override
    public ContentType getContentType() {
        return contentType;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    @Override
    public void setOrder(int order) {
        if (order < 0) {
            throw new IllegalArgumentException("Order must be >= 0");
        }
        this.order = order;
    }

}
