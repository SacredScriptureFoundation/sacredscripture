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
package org.sacredscripture.platform.internal.bible.dao.impl;

import org.sacredscripture.platform.bible.Bible;
import org.sacredscripture.platform.internal.bible.BibleImpl;
import org.sacredscripture.platform.internal.bible.dao.BibleDao;

import org.sacredscripturefoundation.commons.entity.dao.JpaDaoImpl;

import java.util.List;
import java.util.Locale;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;

/**
 * This class is the stock implementation of {@link BibleDao}.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
@ApplicationScoped
public class BibleDaoImpl extends JpaDaoImpl<Bible, BibleImpl, Long> implements BibleDao {

    private static final String NQ_FIND_BY_CODE = "Bible.findByCode";
    private static final String NQ_FIND_BY_LOCALE = "Bible.findByLocale";
    private static final String NQ_FIND_DEFAULT = "Bible.findDefault";

    @Override
    public Bible findByCode(String code) {
        TypedQuery<BibleImpl> q = newNamedQuery(NQ_FIND_BY_CODE);
        q.setParameter("code", code.toLowerCase());
        return singleResultOf(q);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Bible> findByLocale(Locale locale) {
        TypedQuery<BibleImpl> q = newNamedQuery(NQ_FIND_BY_LOCALE);
        q.setParameter("locale", locale.getLanguage());
        return List.class.cast(q.getResultList());
    }

    @Override
    public Bible findDefault() {
        TypedQuery<BibleImpl> q = newNamedQuery(NQ_FIND_DEFAULT);
        return singleResultOf(q);
    }

}
