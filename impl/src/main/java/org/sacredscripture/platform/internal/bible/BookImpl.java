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
import org.sacredscripture.platform.bible.Book;
import org.sacredscripture.platform.bible.BookType;
import org.sacredscripture.platform.bible.BookTypeLocalization;
import org.sacredscripture.platform.bible.Chapter;
import org.sacredscripture.platform.bible.Content;
import org.sacredscripture.platform.bible.ContentKind;
import org.sacredscripture.platform.internal.DataModel.BookTable;
import org.sacredscripture.platform.internal.DataModel.ContentTable;

import org.sacredscripturefoundation.commons.entity.EntityImpl;
import org.sacredscripturefoundation.commons.locale.LocaleContextHolder;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * This class is the stock implementation of {@link Book}.
 *
 * @author Paul Benedict
 * @see BookType
 * @since 1.0
 */
@Entity
@Table(name = BookTable.TABLE_NAME)
public class BookImpl extends EntityImpl<Long> implements Book {

    @ManyToOne(targetEntity = BookTypeImpl.class, optional = false)
    @JoinColumn(name = BookTable.COLUMN_BOOK_TYPE_ID)
    private BookType bookType;

    @Column(name = BookTable.COLUMN_LIST_POSITION)
    private int order;

    @LazyCollection(LazyCollectionOption.EXTRA)
    @OneToMany(targetEntity = ContentImpl.class, mappedBy = "book")
    @OrderBy("order")
    private List<Content> contents;

    @OneToMany(targetEntity = ChapterImpl.class)
    @JoinColumn(name = ContentTable.COLUMN_BOOK_ID, insertable = false, updatable = false)
    @OrderBy("order")
    private List<Chapter> chapters;

    @ManyToOne(targetEntity = BibleImpl.class, optional = false)
    @JoinColumn(name = BookTable.COLUMN_BIBLE_ID)
    private Bible bible;

    @Override
    public void addContent(Content content) {
        content.setBook(this);
        content.setOrder(getContents().size());
        getContents().add(content);

        if (content.getContentKind() == ContentKind.CHAPTER) {
            Chapter chapter = (Chapter) content;
            if (chapters == null) {
                chapters = new LinkedList<>();
            } else {
                if (!chapters.isEmpty()) {
                    Chapter last = chapters.get(chapters.size() - 1);
                    last.setNext(chapter);
                    chapter.setPrevious(last);
                }
            }
            chapters.add(chapter);
        }
    }

    @Override
    public String getAbbreviation() {
        return getAbbreviations().get(0);
    }

    /**
     * Retrieves the localized abbreviations of this book from the book type.
     */
    @Override
    public List<String> getAbbreviations() {
        return localize().getAbbreviations();
    }

    @Override
    public Bible getBible() {
        return bible;
    }

    @Override
    public BookType getBookType() {
        return bookType;
    }

    @Override
    public List<Chapter> getChapters() {
        if (chapters == null) {
            chapters = new LinkedList<>();
        }
        return chapters;
    }

    @Override
    public List<Content> getContents() {
        if (contents == null) {
            contents = new LinkedList<>();
        }
        return contents;
    }

    @Override
    public String getName() {
        return localize().getName();
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public String getTitle() {
        return localize().getTitle();
    }

    private BookTypeLocalization localize() {
        BookTypeLocalization loc = null;
        Locale userLocale = LocaleContextHolder.getLocale();
        if (userLocale != null) {
            loc = bookType.localize(userLocale);
        }
        if (loc == null) {
            loc = bookType.localize(bible.getLocale());
        }
        return loc;
    }

    @Override
    public Book next() {
        List<Book> books = bible.getBooks();
        return (order < books.size() - 1) ? books.get(order + 1) : null;
    }

    @Override
    public Book previous() {
        return (order > 0) ? bible.getBooks().get(order - 1) : null;
    }

    @Override
    public void setBible(Bible bible) {
        this.bible = bible;
    }

    @Override
    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }

    public void setOrder(int order) {
        if (order < 0) {
            throw new IllegalArgumentException("Order cannot be negative");
        }
        this.order = order;
    }

}
