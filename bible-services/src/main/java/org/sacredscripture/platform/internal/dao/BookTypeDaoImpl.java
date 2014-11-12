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
package org.sacredscripture.platform.internal.dao;

import org.sacredscripture.platform.bible.BookType;

import org.sacredscripturefoundation.commons.entity.dao.JpaDaoImpl;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;

/**
 * This class is the stock implementation of {@link BookTypeDao}.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
@ApplicationScoped
public class BookTypeDaoImpl extends JpaDaoImpl<BookType, Long> implements BookTypeDao {

    private static final String NQ_FIND_BY_CODE = "BookType.findByCode";

    @Override
    public BookType findByCode(String code) {
        TypedQuery<BookType> q = newNamedQuery(NQ_FIND_BY_CODE);
        q.setParameter("code", code.toUpperCase());
        return queryForSingleResult(q);
    }

}
