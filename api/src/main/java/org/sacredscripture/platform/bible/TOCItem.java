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
package org.sacredscripture.platform.bible;

import org.sacredscripturefoundation.commons.ParentProvider;

import java.util.List;

/**
 * This interface represents an item in a table of contents. An item is a nested
 * structure. It always contains an associated resource, and it may optionally
 * have a list of children items.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
public interface TOCItem extends ParentProvider<TOCItem> {

    /**
     * Adds the specified child item to this item. The child's
     * {@link #setParent(TOCItem)} is invoked to set this as its parent.
     *
     * @param item the item
     * @see #getChildren()
     * @throws NullPointerException if the item is {@code null}
     */
    void addChild(TOCItem item);

    /**
     * Retrieves the children of this table of contents item.
     *
     * @return the children (never {@code null})
     * @see #addChild(TOCItem)
     */
    List<TOCItem> getChildren();

    /**
     * Retrieves the resource that is associated to this table of contents item.
     *
     * @return the resource (never {@code null}
     */
    Object getResource();

    /**
     * Stores the new parent for this itme.
     *
     * @param parent the parent to set
     * @see #getParent()
     */
    void setParent(TOCItem parent);

}
