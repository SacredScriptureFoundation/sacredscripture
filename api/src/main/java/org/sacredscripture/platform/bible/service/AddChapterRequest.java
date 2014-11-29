package org.sacredscripture.platform.bible.service;

import org.sacredscripturefoundation.commons.ServiceRequestMessage;

public class AddChapterRequest extends ServiceRequestMessage {

    private String bibleCode;
    private String bookTypeCode;
    private String name;

    public final String getBibleCode() {
        return bibleCode;
    }

    public final String getBookTypeCode() {
        return bookTypeCode;
    }

    public final String getName() {
        return name;
    }

    public final void setBibleCode(String bibleCode) {
        this.bibleCode = bibleCode;
    }

    public final void setBookTypeCode(String bookCode) {
        bookTypeCode = bookCode;
    }

    public final void setName(String name) {
        this.name = name;
    }

}
