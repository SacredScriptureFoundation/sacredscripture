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

import org.sacredscripture.platform.bible.Book;

import org.sacredscripturefoundation.commons.ServiceRequestMessage;

import javax.validation.constraints.NotNull;

/**
 * This class is the request to create a new {@link Book} entity.
 *
 * @author Paul Benedict
 * @see BibleMaintenanceService#add(AddBookRequest)
 * @since Sacred Scripture Platform 1.0
 */
public class AddBookRequest extends ServiceRequestMessage {

    String bibleCode;
    String bookTypeCode;

    @NotNull
    public final String getBibleCode() {
        return bibleCode;
    }

    @NotNull
    public final String getBookTypeCode() {
        return bookTypeCode;
    }

    public final void setBibleCode(String bibleCode) {
        this.bibleCode = bibleCode;
    }

    public final void setBookTypeCode(String bookTypeCode) {
        this.bookTypeCode = bookTypeCode;
    }

}
