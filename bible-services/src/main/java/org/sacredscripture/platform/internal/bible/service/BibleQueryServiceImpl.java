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
import org.sacredscripture.platform.bible.service.BibleMaintenanceService;
import org.sacredscripture.platform.bible.service.BibleQueryService;
import org.sacredscripture.platform.bible.service.GetBiblesRequest;
import org.sacredscripture.platform.internal.bible.dao.BibleDao;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
@Local(BibleMaintenanceService.class)
public class BibleQueryServiceImpl implements BibleQueryService {

    @Inject
    private BibleDao bibleDao;

    @Override
    public List<Bible> getBibles(GetBiblesRequest req) {
        List<Bible> bibles;
        if (req.getBibleLocale() != null) {
            bibles = bibleDao.findByLocale(req.getBibleLocale());
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

}