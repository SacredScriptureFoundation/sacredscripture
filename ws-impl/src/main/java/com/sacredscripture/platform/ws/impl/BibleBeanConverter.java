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
import org.sacredscripture.platform.bible.BibleLocalization;

import org.sacredscripturefoundation.commons.locale.LocaleContextHolder;

import org.springframework.core.convert.converter.Converter;

import com.sacredscripture.platform.ws.api.rest.v1.BibleBean;

public class BibleBeanConverter implements Converter<Bible, BibleBean> {

    @Override
    public BibleBean convert(Bible source) {
        BibleBean bean = new BibleBean();

        bean.setAbbreviation(source.getAbbreviation());
        bean.setId(source.getCode());
        bean.setCopyrightNotice(source.getCopyrightNotice());
        bean.setLicense(source.getLicense());
        bean.setName(source.getName());
        bean.setTitle(source.getTitle());

        BibleLocalization loc = source.localize(source.getLocale());
        if (LocaleContextHolder.getLocale() == null || loc.getLocale().equals(LocaleContextHolder.getLocale())) {
            bean.setNativeFlag(true);
        } else {
            bean.setNativeFlag(false);
        }

        if (!bean.isNativeFlag()) {
            bean.setNativeCopyrightNotice(loc.getCopyrightNotice());
            bean.setNativeLicense(loc.getLicense());
            bean.setNativeLocale(loc.getLocale());
            bean.setNativeName(loc.getName());
            bean.setNativeTitle(loc.getTitle());
        }

        return bean;
    }

}
