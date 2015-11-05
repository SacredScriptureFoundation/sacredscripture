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

import org.sacredscripture.platform.bible.Book;
import org.sacredscripture.platform.bible.BookTypeLocalization;

import org.sacredscripturefoundation.commons.locale.LocaleContextHolder;

import java.util.Locale;

import org.springframework.core.convert.converter.Converter;

import com.sacredscripture.platform.ws.api.rest.v1.BookBean;

public class BookBeanConverter implements Converter<Book, BookBean> {

    @Override
    public BookBean convert(Book source) {
        BookBean bean = new BookBean();

        bean.setAbbreviation(source.getAbbreviation());
        bean.setName(source.getName());
        bean.setId(source.getBookType().getCode());
        bean.setTitle(source.getTitle());

        Locale userLocale = LocaleContextHolder.getLocale();
        Locale bibleNativeLocale = source.getBible().getLocale();
        BookTypeLocalization loc = source.getBookType().localize(bibleNativeLocale);
        if (userLocale == null || loc.getLocale().equals(userLocale)) {
            bean.setNativeFlag(true);
            bean.setLocale(bibleNativeLocale);
        } else {
            bean.setNativeFlag(false);
            bean.setNativeAbbreviation(loc.getAbbreviations().get(0));
            bean.setNativeLocale(loc.getLocale());
            bean.setNativeName(loc.getName());
            bean.setNativeTitle(loc.getTitle());
            bean.setLocale(userLocale);
        }

        return bean;
    }

}
