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
package org.sacredscripture.platform.impl.bible.service;

import org.sacredscripture.platform.api.bible.BookType;
import org.sacredscripture.platform.api.bible.BookTypeGroup;
import org.sacredscripture.platform.api.bible.BookTypeGroupLocalization;
import org.sacredscripture.platform.api.bible.BookTypeLocalization;
import org.sacredscripture.platform.api.bible.service.AddBookTypeGroupRequest;
import org.sacredscripture.platform.api.bible.service.AddBookTypeRequest;
import org.sacredscripture.platform.api.bible.service.BibleMaintenanceService;
import org.sacredscripture.platform.api.bible.service.SaveBookTypeGroupLocalizationRequest;
import org.sacredscripture.platform.api.bible.service.SaveBookTypeLocalizationRequest;
import org.sacredscripture.platform.impl.bible.BookTypeGroupImpl;
import org.sacredscripture.platform.impl.bible.BookTypeGroupLocalizationImpl;
import org.sacredscripture.platform.impl.bible.BookTypeImpl;
import org.sacredscripture.platform.impl.bible.BookTypeLocalizationImpl;
import org.sacredscripture.platform.impl.bible.dao.BookTypeDao;
import org.sacredscripture.platform.impl.bible.dao.BookTypeGroupDao;

import org.sacredscripturefoundation.commons.entity.DuplicateEntityException;
import org.sacredscripturefoundation.commons.entity.UnknownEntityException;

import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * This class is the stock implementation of {@link BibleMaintenanceService}.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
// EJB
@Singleton
@Transactional
@Local(BibleMaintenanceService.class)
public class BibleMaintenanceServiceImpl implements BibleMaintenanceService {

    @Inject
    BookTypeDao bookTypeDao;

    @Inject
    BookTypeGroupDao groupDao;

    @Override
    public BookTypeGroup add(AddBookTypeGroupRequest req) {
        // Load the parent if applicable
        BookTypeGroup parent = null;
        if (req.getParentCode() != null) {
            parent = groupDao.findByCode(req.getParentCode());
            if (parent == null) {
                throw new UnknownEntityException(); // FIXME add message
            }
        }

        // Validate code isn't in current use
        if (groupDao.findByCode(req.getCode()) != null) {
            throw new DuplicateEntityException(); // FIXME add message
        }

        // Construct and persist new group
        BookTypeGroupImpl group = new BookTypeGroupImpl();
        group.setCode(req.getCode().toUpperCase());
        if (parent != null) {
            parent.addChild(group);
        }
        groupDao.insert(group);

        return group;
    }

    @Override
    public BookType add(AddBookTypeRequest req) {
        // Lookup the group
        BookTypeGroup group = groupDao.findByCode(req.getGroupCode());
        if (group == null) {
            throw new UnknownEntityException(); // FIXME add message
        }

        // Validate code isn't in current use
        if (bookTypeDao.findByCode(req.getCode()) != null) {
            throw new DuplicateEntityException(); // FIXME add message
        }

        // Construct and persist new book type
        BookTypeImpl bookType = new BookTypeImpl();
        bookType.setCode(req.getCode().toUpperCase());
        group.addChild(group);
        bookTypeDao.insert(bookType);

        return bookType;
    }

    @Override
    public BookTypeGroupLocalization save(SaveBookTypeGroupLocalizationRequest req) {
        BookTypeGroup group = groupDao.findByCode(req.getCode());
        if (group == null) {
            throw new UnknownEntityException(); // FIXME add message
        }

        BookTypeGroupLocalization loc = group.getLocalizedContents().get(req.getLocale());
        if (loc == null) {
            BookTypeGroupLocalizationImpl newLoc = new BookTypeGroupLocalizationImpl();
            newLoc.setLocale(req.getLocale());
            group.addLocalizedContent(newLoc);
            loc = newLoc;
        }

        // Update properties
        loc.setName(req.getName());
        groupDao.update(group);

        return loc;
    }

    @Override
    public BookTypeLocalization save(SaveBookTypeLocalizationRequest req) {
        BookType bookType = bookTypeDao.findByCode(req.getCode());
        if (bookType == null) {
            throw new UnknownEntityException(); // FIXME add message
        }

        BookTypeLocalization loc = bookType.getLocalizedContents().get(req.getLocale());
        if (loc == null) {
            BookTypeLocalizationImpl newLoc = new BookTypeLocalizationImpl();
            newLoc.setLocale(req.getLocale());
            bookType.addLocalizedContent(newLoc);
            loc = newLoc;
        }

        // Update properties
        loc.setName(req.getName());
        loc.setTitle(req.getTitle());
        loc.setAbbreviations(req.getAbbreviations());
        bookTypeDao.update(bookType);

        return loc;
    }

}