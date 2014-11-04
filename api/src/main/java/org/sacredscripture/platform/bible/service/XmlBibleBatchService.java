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

/**
 * This service defines batch operations for maintaining the bible system.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
public interface XmlBibleBatchService {

    /**
     * Populates the database with the bible canon (book types and groups) laid
     * out by the specified document. This method is only needed to setup a new
     * enivronment.
     * <p>
     * This method returns immediately. The loading is done as a batch process.
     *
     * @param docPath the document path accessible to the server
     */
    void loadCanon(String docPath);

}