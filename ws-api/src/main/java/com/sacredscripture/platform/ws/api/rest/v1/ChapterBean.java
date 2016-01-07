/*
 * Copyright (c) 2015, 2016 Sacred Scripture Foundation.
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
package com.sacredscripture.platform.ws.api.rest.v1;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class ChapterBean extends AbstractHypermediaBean {

    /**
     * Key name of the embedded bible.
     *
     * @see #getEmbeddedBible()
     * @see #setEmbeddedBible(BibleBean)
     */
    public static String EMBEDDED_BIBLE = "bible";

    /**
     * Key name of the embedded book.
     *
     * @see #getEmbeddedBook()
     * @see #setEmbeddedBook(BookBean)
     */
    public static String EMBEDDED_BOOK = "book";

    // IMPLEMENTATION NOTE: field order matters!
    // Order listed here is the order rendered in XML/JSON; don't rearrange them
    // unless you are intending to change the rendering order.

    @XmlElement(name = "id")
    private String id;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "contents")
    private List<VerseBean> contents;

    public final void addContent(VerseBean content) {
        if (contents == null) {
            contents = new LinkedList<VerseBean>();
        }
        contents.add(content);
    }

    public final List<VerseBean> getContents() {
        return contents;
    }

    public final BibleBean getEmbeddedBible() {
        return (BibleBean) getEmbedded().get(EMBEDDED_BIBLE);
    }

    public final BookBean getEmbeddedBook() {
        return (BookBean) getEmbedded().get(EMBEDDED_BOOK);
    }

    public final String getId() {
        return id;
    }

    public final String getName() {
        return name;
    }

    public final void setContents(List<VerseBean> contents) {
        this.contents = contents;
    }

    public final void setEmbeddedBible(BibleBean bible) {
        if (bible != null) {
            addEmbedded(EMBEDDED_BIBLE, bible);
        } else {
            removeEmbedded(EMBEDDED_BIBLE);
        }
    }

    public final void setEmbeddedBook(BookBean book) {
        if (book != null) {
            addEmbedded(EMBEDDED_BOOK, book);
        } else {
            removeEmbedded(EMBEDDED_BOOK);
        }
    }

    public final void setId(String id) {
        this.id = id;
    }

    public final void setName(String name) {
        this.name = name;
    }

}
