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

import java.util.Locale;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents an edition of the bible.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
@XmlRootElement
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
public class BibleBean {

    @XmlElement(name = "id")
    private String id;

    @XmlElement(name = "native")
    private boolean nativeFlag;

    @XmlElement(name = "abbreviation")
    private String abbreviation;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "nativeName")
    private String nativeName;

    @XmlElement(name = "title")
    private String title;

    @XmlElement(name = "nativeTitle")
    private String nativeTitle;

    @XmlElement(name = "locale")
    private Locale locale;

    @XmlElement(name = "nativeLocale")
    private Locale nativeLocale;

    @XmlElement(name = "copyrightNotice")
    private String copyrightNotice;

    @XmlElement(name = "nativeCopyrightNotice")
    private String nativeCopyrightNotice;

    @XmlElement(name = "license")
    private String license;

    @XmlElement(name = "nativeLicense")
    private String nativeLicense;

    public final String getAbbreviation() {
        return abbreviation;
    }

    public final String getCopyrightNotice() {
        return copyrightNotice;
    }

    public final String getId() {
        return id;
    }

    public final String getLicense() {
        return license;
    }

    public final Locale getLocale() {
        return locale;
    }

    public final String getName() {
        return name;
    }

    public final String getNativeCopyrightNotice() {
        return nativeCopyrightNotice;
    }

    public final String getNativeLicense() {
        return nativeLicense;
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

    public final String getTitle() {
        return title;
    }

    public final boolean isNativeFlag() {
        return nativeFlag;
    }

    public final void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public final void setCopyrightNotice(String copyrightNotice) {
        this.copyrightNotice = copyrightNotice;
    }

    public final void setId(String id) {
        this.id = id;
    }

    public final void setLicense(String license) {
        this.license = license;
    }

    public final void setLocale(Locale locale) {
        this.locale = locale;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final void setNativeCopyrightNotice(String nativeCopyrightNotice) {
        this.nativeCopyrightNotice = nativeCopyrightNotice;
    }

    public final void setNativeFlag(boolean nativeFlag) {
        this.nativeFlag = nativeFlag;
    }

    public final void setNativeLicense(String nativeLicense) {
        this.nativeLicense = nativeLicense;
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

    public final void setTitle(String title) {
        this.title = title;
    }

}
