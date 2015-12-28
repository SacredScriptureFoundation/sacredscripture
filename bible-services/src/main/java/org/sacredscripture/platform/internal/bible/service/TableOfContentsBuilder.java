/*
 * Copyright (c) 2015 Sacred Scripture Foundation.
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
package org.sacredscripture.platform.internal.bible.service;

import org.sacredscripture.platform.bible.Bible;
import org.sacredscripture.platform.bible.Book;
import org.sacredscripture.platform.bible.TableOfContents;
import org.sacredscripture.platform.bible.TableOfContentsItem;
import org.sacredscripture.platform.bible.canon.BookTypeGroup;
import org.sacredscripture.platform.internal.bible.TableOfContentsImpl;
import org.sacredscripture.platform.internal.bible.TableOfContentsItemImpl;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class TableOfContentsBuilder {

    private final Bible bible;
    private Map<BookTypeGroup, List<Book>> groupToBooks;
    private final List<BookTypeGroup> rootGroups;

    public TableOfContentsBuilder(Bible bible, List<BookTypeGroup> rootGroups) {
        this.bible = Objects.requireNonNull(bible);
        this.rootGroups = Objects.requireNonNull(rootGroups);
    }

    public TableOfContents build() {
        // Create the group-to-books association
        collateBooks();

        // Create the contents
        List<TableOfContentsItem> items = new LinkedList<TableOfContentsItem>();
        for (BookTypeGroup group : rootGroups) {
            TableOfContentsItem item = build0(group);
            if (item != null) {
                items.add(item);
            }
        }

        return new TableOfContentsImpl(items);
    }

    private TableOfContentsItem build0(BookTypeGroup parentGroup) {
        TableOfContentsItem parentItem = null;

        // If books exist, this group is "de facto" a leaf;
        // the design does not support both books and children!
        List<Book> books = groupToBooks.get(parentGroup);
        if (books != null) {
            assert parentGroup.getChildren().isEmpty();
            parentItem = new TableOfContentsItemImpl(parentGroup);
            for (Book book : books) {
                parentItem.addChild(new TableOfContentsItemImpl(book));
            }
            return parentItem;
        }

        // No books in this group...
        // Go through children recursively to see if there are books
        // in any descendants
        for (BookTypeGroup childGroup : parentGroup.getChildren()) {
            TableOfContentsItem childItem = build0(childGroup);
            if (childItem != null) {
                if (parentItem == null) {
                    parentItem = new TableOfContentsItemImpl(parentGroup);
                }
                parentItem.addChild(childItem);
            }
        }
        return parentItem;
    }

    /**
     * Separates out the books by group and collects them together.
     */
    private void collateBooks() {
        groupToBooks = new LinkedHashMap<BookTypeGroup, List<Book>>();
        for (Book book : bible.getBooks()) {
            BookTypeGroup group = book.getBookType().getBookTypeGroup();
            List<Book> booksOfGroup = groupToBooks.get(group);
            if (booksOfGroup == null) {
                booksOfGroup = new LinkedList<Book>();
                groupToBooks.put(group, booksOfGroup);
            }
            booksOfGroup.add(book);
        }
    }

}