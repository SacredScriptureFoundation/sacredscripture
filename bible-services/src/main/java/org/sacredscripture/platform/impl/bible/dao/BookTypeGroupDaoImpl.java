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
package org.sacredscripture.platform.impl.bible.dao;

import org.sacredscripture.platform.api.bible.BookTypeGroup;

import org.sacredscripturefoundation.commons.entity.dao.JpaDaoImpl;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * This class is the stock implementation of {@link BookTypeGroupDao}.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
@ApplicationScoped
public class BookTypeGroupDaoImpl extends JpaDaoImpl<BookTypeGroup, Long> implements BookTypeGroupDao {

    private static final String NQ_FIND_BY_CODE = "BookTypeGroupDao.findByCode";

    @Override
    public BookTypeGroup findByCode(String code) {
        TypedQuery<BookTypeGroup> q = newNamedQuery(NQ_FIND_BY_CODE);
        q.setParameter("code", code.toUpperCase());
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
