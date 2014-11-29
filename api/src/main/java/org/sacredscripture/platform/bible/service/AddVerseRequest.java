package org.sacredscripture.platform.bible.service;

import org.sacredscripturefoundation.commons.ServiceRequestMessage;

public class AddVerseRequest extends ServiceRequestMessage {

    private Long chapterId;
    private String name;
    private String altName;
    private boolean omitted;
    private String text;
    private String code;

    public final String getAltName() {
        return altName;
    }

    public final Long getChapterId() {
        return chapterId;
    }

    public final String getCode() {
        return code;
    }

    public final String getName() {
        return name;
    }

    public final String getText() {
        return text;
    }

    public final boolean isOmitted() {
        return omitted;
    }

    public final void setAltName(String altName) {
        this.altName = altName;
    }

    public final void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public final void setCode(String code) {
        this.code = code;
    }

    public final void setName(String verseName) {
        name = verseName;
    }

    public final void setOmitted(boolean omitted) {
        this.omitted = omitted;
    }

    public final void setText(String text) {
        this.text = text;
    }

}
