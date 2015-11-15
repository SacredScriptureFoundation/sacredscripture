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

import org.sacredscripture.platform.bible.TableOfContents;
import org.sacredscripture.platform.bible.TableOfContentsItem;

import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

/**
 * This class is the stock implementation of {@link TableOfContents}.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
public class TableOfContentsImpl implements TableOfContents {

    private final List<TableOfContentsItem> items;

    public TableOfContentsImpl(List<TableOfContentsItem> items) {
        this.items = Objects.requireNonNull(items);
    }

    @Override
    public List<TableOfContentsItem> getItems() {
        return items;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toStringNested(items, "", sb);
        return sb.toString();
    }

    private void toStringNested(List<TableOfContentsItem> items, String path, StringBuilder sb) {
        for (ListIterator<TableOfContentsItem> i = items.listIterator(); i.hasNext();) {
            TableOfContentsItem item = i.next();
            String itemPath = path + "/" + i.nextIndex();
            sb.append(itemPath);
            sb.append("[");
            sb.append(Objects.toString(item.getResource()));
            sb.append("]");
            if (!item.getChildren().isEmpty()) {
                sb.append(", ");
                toStringNested(item.getChildren(), itemPath, sb);
            }
            if (i.hasNext()) {
                sb.append(", ");
            }
        }
    }

}
