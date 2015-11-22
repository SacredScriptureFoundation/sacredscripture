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
package org.sacredscripture.platform.internal.bible.service;

import org.sacredscripture.platform.bible.Bible;
import org.sacredscripture.platform.bible.Chapter;
import org.sacredscripture.platform.bible.Content;
import org.sacredscripture.platform.bible.TableOfContents;
import org.sacredscripture.platform.bible.service.BibleQueryService;
import org.sacredscripture.platform.internal.bible.dao.BibleDao;
import org.sacredscripture.platform.internal.bible.dao.BookTypeGroupDao;
import org.sacredscripture.platform.internal.bible.dao.ContentDao;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

/**
 * This class is the stock implementation of {@link BibleQueryService}.
 * <p>
 * Database transactions are not required for this service, but it will run in
 * one if a transaction is already present.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
@Singleton
@Transactional(TxType.SUPPORTS)
@Local(BibleQueryService.class)
public class BibleQueryServiceImpl implements BibleQueryService {

    @Inject
    private BibleDao bibleDao;
    @Inject
    private BookTypeGroupDao bookTypeGroupDao;
    @Inject
    private ContentDao contentDao;

    @Override
    public Bible getBible(String bibleCode) {
        return bibleDao.findByCode(bibleCode);
    }

    @Override
    public List<Bible> getBibles(Locale bibleLocale) {
        List<Bible> bibles;
        if (bibleLocale != null) {
            bibles = bibleDao.findByLocale(bibleLocale);
        } else {
            bibles = bibleDao.getAll();
        }

        // TODO replace with LocalizedNameComparator?
        Collections.sort(bibles, new Comparator<Bible>() {
            @Override
            public int compare(Bible o1, Bible o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        return bibles;
    }

    @Override
    public List<Chapter> getChapters(String bibleCode, String bookCode) {
        return contentDao.findChapters(bibleCode, bookCode);
    }

    @Override
    public Content getContent(String contentId) {
        return contentDao.get(Long.valueOf(contentId), false);
    }

    @Override
    public TableOfContents getTableOfContents(String bibleCode) {
        Bible bible = bibleDao.findByCode(bibleCode);
        if (bible == null) {
            return null;
        }
        return new TableOfContentsBuilder(bible, bookTypeGroupDao.findRoots()).build();
    }
}
