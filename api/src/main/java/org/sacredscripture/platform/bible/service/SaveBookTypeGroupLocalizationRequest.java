/*
 * Copyright (c) 2014, 2015 Sacred Scripture Foundation.
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

import org.sacredscripture.platform.bible.canon.BookTypeGroup;

import org.sacredscripturefoundation.commons.ServiceRequestMessage;

import java.util.Locale;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * This class is the request to create or update the localized data for a
 * {@link BookTypeGroup} entity.
 *
 * @author Paul Benedict
 * @see BibleMaintenanceService#save(SaveBookTypeGroupLocalizationRequest)
 * @since Sacred Scripture Platform 1.0
 */
public final class SaveBookTypeGroupLocalizationRequest extends ServiceRequestMessage {

    private String code;
    private Locale locale;
    private String name;

    @NotNull
    @Size(min = 3, max = 3)
    public String getCode() {
        return code;
    }

    @NotNull
    public Locale getLocale() {
        return locale;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setCode(String bookTypeCode) {
        code = bookTypeCode;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setName(String name) {
        this.name = name;
    }

}
