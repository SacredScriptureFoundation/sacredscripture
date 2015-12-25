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

import org.sacredscripture.platform.bible.Chapter;

import org.sacredscripturefoundation.commons.ServiceRequestMessage;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * This class is the request to create a new {@link Chapter} entity.
 *
 * @author Paul Benedict
 * @see BibleMaintenanceService#add(AddChapterRequest)
 * @since Sacred Scripture Platform 1.0
 */
public class AddChapterRequest extends ServiceRequestMessage {

    private String bibleCode;
    private String bookTypeCode;
    private String publicId;
    private String code;
    private String name;

    @NotNull
    @Size(max = 10)
    public final String getBibleCode() {
        return bibleCode;
    }

    @NotNull
    @Size(min = 3, max = 3)
    public final String getBookTypeCode() {
        return bookTypeCode;
    }

    @Size(max = 10)
    public final String getCode() {
        return code;
    }

    @NotNull
    @Size(max = 10)
    public final String getName() {
        return name;
    }

    @NotNull
    @Size(min = 22, max = 22)
    public final String getPublicId() {
        return publicId;
    }

    public final void setBibleCode(String bibleCode) {
        this.bibleCode = bibleCode;
    }

    public final void setBookTypeCode(String bookCode) {
        bookTypeCode = bookCode;
    }

    public final void setCode(String code) {
        this.code = code;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final void setPublicId(String publicId) {
        this.publicId = publicId;
    }

}
