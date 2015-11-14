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
package org.sacredscripture.platform.internal.bible;

import org.sacredscripture.platform.bible.TOCItem;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * This class is the stock implementation of {@link TOCItem}.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
public class TOCItemImpl implements TOCItem {

    private final Object resource;
    private TOCItem parent;
    private List<TOCItem> children;

    public TOCItemImpl(Object resource) {
        this.resource = Objects.requireNonNull(resource);
    }

    @Override
    public void addChild(TOCItem item) {
        getChildren().add(item);
        item.setParent(this);
    }

    @Override
    public List<TOCItem> getChildren() {
        if (children != null) {
            children = new LinkedList<>();
        }
        return children;
    }

    @Override
    public TOCItem getParent() {
        return parent;
    }

    @Override
    public Object getResource() {
        return resource;
    }

    @Override
    public void setParent(TOCItem parent) {
        this.parent = parent;
    }

}
