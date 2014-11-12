/*
 * Copyright (c) 2014 Sacred Scripture Foundation.
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
package org.sacredscripture.platform.bible.service;

import org.sacredscripture.platform.bible.Bible;

import org.sacredscripturefoundation.commons.ServiceRequestMessage;

import java.util.Locale;

import javax.validation.constraints.NotNull;

/**
 * This class is the request to create or update a {@link Bible} entity.
 *
 * @author Paul Benedict
 * @see BibleMaintenanceService#save(SaveBibleRequest)
 * @since Sacred Scripture Platform 1.0
 */
public class SaveBibleRequest extends ServiceRequestMessage {

    private String code;
    private Locale locale;
    private boolean rightToLeftReading;
    private String abbreviation;
    private String name;
    private String copyrightNotice;
    private String license;
    private String title;

    @NotNull
    public final String getAbbreviation() {
        return abbreviation;
    }

    @NotNull
    public final String getCode() {
        return code;
    }

    @NotNull
    public final String getCopyrightNotice() {
        return copyrightNotice;
    }

    @NotNull
    public final String getLicense() {
        return license;
    }

    @NotNull
    public final Locale getLocale() {
        return locale;
    }

    @NotNull
    public final String getName() {
        return name;
    }

    @NotNull
    public final String getTitle() {
        return title;
    }

    public final boolean isRightToLeftReading() {
        return rightToLeftReading;
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

    public final void setRightToLeftReading(boolean rightToLeftReading) {
        this.rightToLeftReading = rightToLeftReading;
    }

    public final void setTitle(String title) {
        this.title = title;
    }

}
