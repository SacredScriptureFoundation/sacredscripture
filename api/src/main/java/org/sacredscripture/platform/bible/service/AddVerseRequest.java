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

import org.sacredscripture.platform.bible.Verse;

import org.sacredscripturefoundation.commons.ServiceRequestMessage;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * This class is the request to create a new {@link Verse} entity.
 *
 * @author Paul Benedict
 * @see BibleMaintenanceService#add(AddVerseRequest)
 * @since Sacred Scripture Platform 1.0
 */
public class AddVerseRequest extends ServiceRequestMessage {

    private Long chapterId;
    private String name;
    private String altName;
    private boolean omitted;
    private String text;
    private String code;

    @NotNull
    @Size(max = 10)
    public final String getAltName() {
        return altName;
    }

    @NotNull
    public final Long getChapterId() {
        return chapterId;
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
    @Size(max = 2000)
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
