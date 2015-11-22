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
package com.sacredscripture.platform.ws.api.rest.v1;

import java.util.List;
import java.util.Locale;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents a book from a {@link BibleBean}.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
@XmlRootElement
public class BookBean extends AbstractHypermediaBean {

    // IMPLEMENTATION NOTE: field order matters!
    // Order listed here is the order rendered in XML/JSON; don't rearrange them
    // unless you are intending to change the rendering order.

    @XmlElement(name = "id")
    private String id;

    @XmlElement(name = "locale")
    private Locale locale;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "title")
    private String title;

    @XmlElement(name = "abbreviation")
    private String abbreviation;

    @XmlElement(name = "native")
    private boolean nativeFlag;

    @XmlElement(name = "nativeLocale")
    private Locale nativeLocale;

    @XmlElement(name = "nativeAbbreviation")
    private String nativeAbbreviation;

    @XmlElement(name = "nativeName")
    private String nativeName;

    @XmlElement(name = "nativeTitle")
    private String nativeTitle;

    @XmlElement(name = "chapters")
    private List<ChapterBean> chapters;

    public final String getAbbreviation() {
        return abbreviation;
    }

    public final String getId() {
        return id;
    }

    public final Locale getLocale() {
        return locale;
    }

    public final String getName() {
        return name;
    }

    public final String getNativeAbbreviation() {
        return nativeAbbreviation;
    }

    public final Locale getNativeLocale() {
        return nativeLocale;
    }

    public final String getNativeName() {
        return nativeName;
    }

    public final String getNativeTitle() {
        return nativeTitle;
    }

    public final List<ChapterBean> getChapters() {
        return chapters;
    }

    public final String getTitle() {
        return title;
    }

    public final boolean isNativeFlag() {
        return nativeFlag;
    }

    public final void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public final void setId(String id) {
        this.id = id;
    }

    public final void setLocale(Locale locale) {
        this.locale = locale;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final void setNativeAbbreviation(String nativeAbbreviation) {
        this.nativeAbbreviation = nativeAbbreviation;
    }

    public final void setNativeFlag(boolean nativeFlag) {
        this.nativeFlag = nativeFlag;
    }

    public final void setNativeLocale(Locale nativeLocale) {
        this.nativeLocale = nativeLocale;
    }

    public final void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public final void setNativeTitle(String nativeTitle) {
        this.nativeTitle = nativeTitle;
    }

    public final void setChapters(List<ChapterBean> sections) {
        this.chapters = sections;
    }

    public final void setTitle(String title) {
        this.title = title;
    }

}
