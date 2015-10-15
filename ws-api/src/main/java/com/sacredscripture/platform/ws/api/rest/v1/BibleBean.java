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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents an edition of the bible.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
@XmlRootElement
public class BibleBean {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "abbreviation")
    private String abbreviation;

    @XmlElement(name = "title")
    private String title;

    @XmlElement(name = "locale")
    private Locale locale;

    @XmlElement(name = "code")
    private String code;

    @XmlElement(name = "copyrightNotice")
    private String copyrightNotice;

    @XmlElement(name = "license")
    private String license;

    public final String getAbbreviation() {
        return abbreviation;
    }

    public final String getCode() {
        return code;
    }

    public final String getCopyrightNotice() {
        return copyrightNotice;
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

    public final String getTitle() {
        return title;
    }

    public final void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public final void setCode(String code) {
        this.code = code;
    }

    public final void setCopyrightNotice(String copyrightNotice) {
        this.copyrightNotice = copyrightNotice;
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

    public final void setTitle(String title) {
        this.title = title;
    }

}
