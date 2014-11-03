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

import org.sacredscripture.platform.bible.BookTypeGroup;

import org.sacredscripturefoundation.commons.ServiceRequestMessage;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * This class is the request to create a new {@link BookTypeGroup} entity.
 *
 * @author Paul Benedict
 * @see BibleMaintenanceService#add(AddBookTypeGroupRequest)
 * @since Sacred Scripture Platform 1.0
 */
public final class AddBookTypeGroupRequest extends ServiceRequestMessage {

    private String parentCode;
    private String code;

    @NotNull
    @Size(min = 3, max = 3)
    public String getCode() {
        return code;
    }

    @Size(min = 3, max = 3)
    public String getParentCode() {
        return parentCode;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

}
