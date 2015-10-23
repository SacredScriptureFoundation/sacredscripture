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
package com.sacredscripture.platform.ws.impl;

import org.sacredscripture.platform.bible.Bible;
import org.sacredscripture.platform.bible.service.BibleQueryService;

import org.sacredscripturefoundation.commons.locale.LocaleContextHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;

import org.springframework.core.convert.ConversionService;

import com.sacredscripture.platform.ws.api.rest.v1.BibleBean;
import com.sacredscripture.platform.ws.api.rest.v1.BibleResource;

@ApplicationScoped
public class BibleResourceImpl extends AbstractSpringAwareResource implements BibleResource {

    @EJB
    private BibleQueryService queryService;
    private ConversionService conversionService;

    @Override
    public List<BibleBean> getBibles(Locale locale) {
        LocaleContextHolder.setLocale(locale);
        List<Bible> bibles = queryService.getBibles(null);
        if (!bibles.isEmpty()) {
            List<BibleBean> results = new ArrayList<>(bibles.size());
            for (BibleBean bibleBean : results) {
                conversionService.convert(bibleBean, Bible.class);
            }
            return results;
        } else {
            return Collections.emptyList();
        }

    }

    @PostConstruct
    void resolveSpringBeans() {
        conversionService = springBean(ConversionService.class);
    }

}
