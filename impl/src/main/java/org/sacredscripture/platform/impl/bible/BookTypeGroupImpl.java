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

import org.sacredscripture.platform.api.bible.BookType;
import org.sacredscripture.platform.api.bible.BookTypeGroup;
import org.sacredscripture.platform.api.bible.BookTypeGroupLocalization;
import org.sacredscripture.platform.impl.DataModel.BookTypeGroupTable;

import org.sacredscripturefoundation.commons.locale.entity.LocalizableEntity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * This class is the stock implementation of {@link BookTypeGroup}.
 *
 * @author Paul Benedict
 * @see BookTypeGroupLocalizationImpl
 * @since Sacred Scripture Platform 1.0
 */
@Entity
@Table(name = BookTypeGroupTable.TABLE_NAME)
public class BookTypeGroupImpl extends LocalizableEntity<Long, BookTypeGroupLocalization> implements BookTypeGroup {

    // @OneToMany(targetEntity = BookImpl.class, mappedBy = "bookTypeGroup")
    // @JoinColumn(name = BookTable.COLUMN_BOOK_TYPE_ID)
    // @OrderColumn(name = BookTable.COLUMN_LIST_POSITION)
    @Transient
    private List<BookType> bookTypes;

    // @OneToMany(targetEntity = BookTypeGroupLocalizationImpl.class)
    // @MapKeyJoinColumn(name =
    // BookTypeGroupLocalizationTable.COLUMN_BOOK_TYPE_GROUP_ID)
    // @MapKey(name = "locale")
    @Transient
    private Map<Locale, BookTypeGroupLocalization> localizedContents;

    @ManyToOne(targetEntity = BookTypeGroupImpl.class)
    @JoinColumn(name = BookTypeGroupTable.COLUMN_PARENT_ID)
    private BookTypeGroup parent;

    @OneToMany(targetEntity = BookTypeGroupImpl.class, mappedBy = "parent")
    @OrderBy("order")
    List<BookTypeGroup> children;

    @Column(name = BookTypeGroupTable.COLUMN_LIST_POSITION)
    int order;

    @Override
    public void addChild(BookTypeGroup group) {
        Objects.requireNonNull(group);
        group.setParent(this);
        group.setOrder(getChildren().size());
        getChildren().add(group);
    }

    @Override
    public List<BookType> getBookTypes() {
        if (bookTypes == null) {
            bookTypes = new LinkedList<>();
        }
        return bookTypes;
    }

    @Override
    public List<BookTypeGroup> getChildren() {
        if (children == null) {
            children = new LinkedList<>();
        }
        return children;
    }

    @Override
    public Map<Locale, BookTypeGroupLocalization> getLocalizedContents() {
        if (localizedContents == null) {
            localizedContents = new HashMap<>();
        }
        return localizedContents;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public BookTypeGroup getParent() {
        return parent;
    }

    @Override
    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public void setParent(BookTypeGroup parent) {
        this.parent = parent;
    }

}