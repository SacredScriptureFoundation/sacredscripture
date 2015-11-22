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

import javax.xml.bind.annotation.XmlElement;

public class ChapterBean extends AbstractHypermediaBean {

    // IMPLEMENTATION NOTE: field order matters!
    // Order listed here is the order rendered in XML/JSON; don't rearrange them
    // unless you are intending to change the rendering order.

    @XmlElement(name = "id")
    private String id;

    @XmlElement(name = "name")
    private String name;

    public final String getId() {
        return id;
    }

    public final String getName() {
        return name;
    }

    public final void setId(String id) {
        this.id = id;
    }

    public final void setName(String name) {
        this.name = name;
    }

}
