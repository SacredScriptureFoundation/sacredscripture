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
package org.sacredscripture.platform.internal.bible.service;

import org.sacredscripture.platform.bible.Bible;
import org.sacredscripture.platform.bible.BibleLocalization;
import org.sacredscripture.platform.bible.Book;
import org.sacredscripture.platform.bible.Chapter;
import org.sacredscripture.platform.bible.Content;
import org.sacredscripture.platform.bible.ContentKind;
import org.sacredscripture.platform.bible.Verse;
import org.sacredscripture.platform.bible.VerseText;
import org.sacredscripture.platform.bible.canon.BookType;
import org.sacredscripture.platform.bible.canon.BookTypeGroup;
import org.sacredscripture.platform.bible.canon.BookTypeGroupLocalization;
import org.sacredscripture.platform.bible.canon.BookTypeLocalization;
import org.sacredscripture.platform.bible.service.AddBookRequest;
import org.sacredscripture.platform.bible.service.AddBookTypeGroupRequest;
import org.sacredscripture.platform.bible.service.AddBookTypeRequest;
import org.sacredscripture.platform.bible.service.AddChapterRequest;
import org.sacredscripture.platform.bible.service.AddVerseRequest;
import org.sacredscripture.platform.bible.service.BibleMaintenanceService;
import org.sacredscripture.platform.bible.service.SaveBibleRequest;
import org.sacredscripture.platform.bible.service.SaveBookTypeGroupLocalizationRequest;
import org.sacredscripture.platform.bible.service.SaveBookTypeLocalizationRequest;
import org.sacredscripture.platform.internal.bible.BibleImpl;
import org.sacredscripture.platform.internal.bible.BibleLocalizationImpl;
import org.sacredscripture.platform.internal.bible.BookImpl;
import org.sacredscripture.platform.internal.bible.BookTypeGroupImpl;
import org.sacredscripture.platform.internal.bible.BookTypeGroupLocalizationImpl;
import org.sacredscripture.platform.internal.bible.BookTypeImpl;
import org.sacredscripture.platform.internal.bible.BookTypeLocalizationImpl;
import org.sacredscripture.platform.internal.bible.ChapterImpl;
import org.sacredscripture.platform.internal.bible.VerseImpl;
import org.sacredscripture.platform.internal.bible.VerseTextImpl;
import org.sacredscripture.platform.internal.bible.dao.BibleDao;
import org.sacredscripture.platform.internal.bible.dao.BookDao;
import org.sacredscripture.platform.internal.bible.dao.BookTypeDao;
import org.sacredscripture.platform.internal.bible.dao.BookTypeGroupDao;
import org.sacredscripture.platform.internal.bible.dao.ContentDao;

import org.sacredscripturefoundation.commons.entity.DuplicateEntityException;
import org.sacredscripturefoundation.commons.entity.UnknownEntityException;

import java.util.List;
import java.util.Objects;

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
@Singleton
@Transactional
@Local(BibleMaintenanceService.class)
public class BibleMaintenanceServiceImpl implements BibleMaintenanceService {

    // Error message templates
    private static final String ERR_BIBLE_CODE = "Bible [%s]";
    private static final String ERR_BOOK_TYPE_CODE = "Book type [%s]";
    private static final String ERR_BOOK_TYPE_GROUP_CODE = "Book type group [%s]";
    private static final String ERR_CHAPTER_ID = "Chapter ID [%s]";

    @Inject
    private BibleDao bibleDao;

    @Inject
    private BookDao bookDao;

    @Inject
    private BookTypeDao bookTypeDao;

    @Inject
    private BookTypeGroupDao groupDao;

    @Inject
    private ContentDao contentDao;

    @Override
    public Book add(AddBookRequest req) {
        // Lookup the bible
        Bible bible = bibleDao.findByCode(req.getBibleCode());
        if (bible == null) {
            throw new UnknownEntityException(ERR_BIBLE_CODE, req.getBibleCode());
        }

        // Lookup the book type
        BookType bookType = bookTypeDao.findByCode(req.getBookTypeCode());
        if (bookType == null) {
            throw new UnknownEntityException(ERR_BOOK_TYPE_CODE, req.getBookTypeCode());
        }

        // Verify the bible isn't already using the book type
        if (bible.getBook(req.getBookTypeCode()) != null) {
            throw new DuplicateEntityException(ERR_BOOK_TYPE_CODE, req.getBookTypeCode());
        }

        BookImpl book = new BookImpl();
        book.setBookType(bookType);
        bible.addBook(book);

        bookDao.insert(book);
        return book;
    }

    @Override
    public BookTypeGroup add(AddBookTypeGroupRequest req) {
        // Load the parent if applicable
        BookTypeGroup parent = null;
        if (req.getParentCode() != null) {
            parent = groupDao.findByCode(req.getParentCode());
            if (parent == null) {
                throw new UnknownEntityException(ERR_BOOK_TYPE_GROUP_CODE, req.getParentCode());
            }
        }

        // Validate code isn't in current use
        if (groupDao.findByCode(req.getCode()) != null) {
            throw new DuplicateEntityException(ERR_BOOK_TYPE_GROUP_CODE, req.getParentCode());
        }

        // Construct and persist new group
        BookTypeGroupImpl group = new BookTypeGroupImpl();
        group.setCode(req.getCode());
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
            throw new UnknownEntityException(ERR_BOOK_TYPE_GROUP_CODE, req.getGroupCode());
        }

        // Validate code isn't in current use
        if (bookTypeDao.findByCode(req.getCode()) != null) {
            throw new DuplicateEntityException(ERR_BOOK_TYPE_GROUP_CODE, req.getCode());
        }

        // Construct and persist new book type
        BookTypeImpl bookType = new BookTypeImpl();
        bookType.setCode(req.getCode());
        bookType.setOrder((int) bookTypeDao.count());
        group.addBookType(bookType);
        bookTypeDao.insert(bookType);

        return bookType;
    }

    @Override
    public Chapter add(AddChapterRequest req) {
        // Find bible
        String bibleCode = req.getBibleCode();
        Bible bible = bibleDao.findByCode(bibleCode);
        if (bible == null) {
            throw new UnknownEntityException(ERR_BIBLE_CODE, bibleCode);
        }

        // Find book
        String bookCode = req.getBookTypeCode();
        Book book = bible.getBook(bookCode);
        if (book == null) {
            throw new UnknownEntityException(ERR_BOOK_TYPE_CODE, bookCode);
        }

        // Find previous chapter
        Chapter previous = null;
        List<Chapter> chapters = contentDao.findChapters(bible.getPublicId(), book.getOrder());
        if (!chapters.isEmpty()) {
            previous = chapters.get(chapters.size() - 1);
        }

        // Build and persist new chapter
        ChapterImpl chapter = new ChapterImpl();
        chapter.setName(req.getName());
        chapter.setCode(req.getCode());
        chapter.setPublicId(req.getPublicId());
        chapter.setPrevious(previous);
        book.addContent(chapter);
        contentDao.insert(chapter);

        // Update previous chapter with next reference
        if (previous != null) {
            previous.setNext(chapter);
            contentDao.update(previous);
        }

        return chapter;
    }

    @Override
    public Verse add(AddVerseRequest req) {
        Content content = contentDao.get(req.getChapterId(), true);
        if (content == null || content.getContentKind() != ContentKind.CHAPTER) {
            throw new UnknownEntityException(ERR_CHAPTER_ID, req.getChapterId());
        }

        VerseImpl verse = new VerseImpl();
        verse.setAltName(req.getAltName());
        verse.setBook(content.getBook());
        verse.setChapter((Chapter) content);
        verse.setCode(req.getCode());
        verse.setName(req.getName());
        verse.setOmitted(req.isOmitted());
        verse.setPublicId(req.getPublicId());

        VerseText text = new VerseTextImpl();
        text.setText(req.getText());
        verse.setText(text);

        content.getBook().addContent(verse);
        contentDao.insert(verse);

        return verse;
    }

    @Override
    public Bible findBibleByCode(String bibleCode) {
        Objects.requireNonNull(bibleCode);
        return bibleDao.findByCode(bibleCode);
    }

    @Override
    public Bible save(SaveBibleRequest req) {
        // Find or create the bible
        Bible bible = bibleDao.findByCode(req.getCode());
        if (bible == null) {
            BibleImpl newBible = new BibleImpl();
            newBible.setCode(req.getCode().toLowerCase());
            newBible.setLocale(req.getLocale());
            newBible.setRightToLeftReading(req.isRightToLeftReading());
            newBible.setPublicId(req.getId());
            bibleDao.insert(newBible);
            bible = newBible;
        }

        // Create or update the default localization
        BibleLocalization loc = bible.getLocalizedContents().get(req.getLocale());
        if (loc == null) {
            loc = new BibleLocalizationImpl();
            loc.setLocale(req.getLocale());
            bible.addLocalizedContent(loc);
        }
        loc.setAbbreviation(req.getAbbreviation());
        loc.setCopyrightNotice(req.getCopyrightNotice());
        loc.setName(req.getName());
        loc.setLicense(req.getLicense());
        loc.setTitle(req.getTitle());
        bibleDao.update(bible);

        return bible;
    }

    @Override
    public BookTypeGroupLocalization save(SaveBookTypeGroupLocalizationRequest req) {
        BookTypeGroup group = groupDao.findByCode(req.getCode());
        if (group == null) {
            throw new UnknownEntityException(ERR_BOOK_TYPE_GROUP_CODE, req.getCode());
        }

        BookTypeGroupLocalization loc = group.getLocalizedContents().get(req.getLocale());
        if (loc == null) {
            loc = new BookTypeGroupLocalizationImpl();
            loc.setLocale(req.getLocale());
            group.addLocalizedContent(loc);
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
            throw new UnknownEntityException(ERR_BOOK_TYPE_CODE, req.getCode());
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
