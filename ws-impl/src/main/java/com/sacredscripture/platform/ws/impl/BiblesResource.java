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
import org.sacredscripture.platform.bible.Book;
import org.sacredscripture.platform.bible.service.BibleQueryService;

import org.sacredscripturefoundation.commons.locale.LocaleContextHolder;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.springframework.core.convert.ConversionService;

import com.sacredscripture.platform.ws.api.rest.v1.BibleBean;
import com.sacredscripture.platform.ws.api.rest.v1.BookBean;
import com.sacredscripture.platform.ws.api.rest.v1.StandardLinkRelation;

@Path(BiblesResource.ROOT_PATH)
@Produces("application/json")
public class BiblesResource extends AbstractSpringAwareResource {

    // Paths
    public static final String ROOT_PATH = "/rest/v1/bibles";
    public static final String SUB_PATH_BIBLE = "/{bible}";
    public static final String SUB_PATH_BOOKS = "/{bible}/books";
    public static final String SUB_PATH_BOOK = "/{bible}/books/{book}";

    @EJB
    private BibleQueryService queryService;
    private ConversionService conversionService;

    @Path(SUB_PATH_BIBLE)
    @GET
    public Response getBible(
            @PathParam("bible") String bibleCode,
            @QueryParam("bg") int bookGroupDepth,
            @QueryParam("lang") Locale locale,
            @QueryParam("mcr") boolean multipleChoicesRedirect,
            @Context UriInfo uriInfo) {
        // Does the bible exist?
        Bible bible = queryService.getBible(bibleCode);
        if (bible == null) {
            return Response.status(Status.NOT_FOUND).build();
        }

        // Is the user's locale supported? If not, present choices to user
        Locale userLocale;
        if (locale != null) {
            if (!bible.supportsLocale(locale)) {
                return ResponseUtils.multipleChoices(
                    bible.getLocale(),
                    bible.locales(),
                    multipleChoicesRedirect ? uriInfo.getBaseUriBuilder().path(SUB_PATH_BIBLE) : null,
                    bibleCode);
            }
            userLocale = locale;
        } else {
            userLocale = bible.getLocale();
        }

        // Prepare response
        LocaleContextHolder.setLocale(userLocale);
        BibleBean bibleBean = conversionService.convert(bible, BibleBean.class);
        bibleBean.addLink(uriInfo.getAbsolutePath().toString(), StandardLinkRelation.SELF.rel());
        for (Locale loc : bible.getLocalizedContents().keySet()) {
            if (!loc.equals(userLocale)) {
                bibleBean.addLink(
                    ResponseUtils.alternateLocalizedUri(uriInfo.getAbsolutePathBuilder(), loc).toString(),
                    StandardLinkRelation.ALTERNATE.rel(),
                    loc);
            }
        }

        // Send response
        return Response.ok().entity(bibleBean).build();
    }

    @GET
    public Response getBibles() {
        List<Bible> bibles = queryService.getBibles(null);
        List<BibleBean> bibleBeans = new LinkedList<>();
        for (Bible bible : bibles) {
            bibleBeans.add(conversionService.convert(bible, BibleBean.class));
        }
        return Response.ok().entity(bibleBeans).build();
    }

    @Path(SUB_PATH_BOOK)
    @GET
    public Response getBook(
            @PathParam("bible") String bibleCode,
            @PathParam("book") String bookCode,
            @QueryParam("lang") Locale locale,
            @QueryParam("mcr") boolean multipleChoicesRedirect,
            @Context UriInfo uriInfo) {
        // Does the bible exist?
        Bible bible = queryService.getBible(bibleCode);
        if (bible == null) {
            return Response.status(Status.NOT_FOUND).build();
        }

        // Does the book exist?
        Book book = bible.getBook(bookCode);
        if (book == null) {
            return Response.status(Status.NOT_FOUND).build();
        }

        // Is the user's locale supported? If not, present choices to user
        Locale userLocale;
        if (locale != null) {
            if (!book.getBookType().supportsLocale(locale)) {
                return ResponseUtils.multipleChoices(
                    bible.getLocale(),
                    book.getBookType().locales(),
                    multipleChoicesRedirect ? uriInfo.getBaseUriBuilder().path(SUB_PATH_BOOK) : null,
                    bibleCode,
                    bookCode);
            }
            userLocale = locale;
        } else {
            userLocale = bible.getLocale();
        }

        // Prepare response
        LocaleContextHolder.setLocale(userLocale);
        BookBean bookBean = conversionService.convert(book, BookBean.class);

        // Send response
        return Response.ok().entity(bookBean).build();
    }

    @Path(SUB_PATH_BOOKS)
    @GET
    public Response getBooks(
            @PathParam("bible") String bibleCode,
            @QueryParam("lang") Locale locale,
            @QueryParam("mcr") boolean multipleChoicesRedirect,
            @Context UriInfo uriInfo) {
        // Does the bible exist?
        Bible bible = queryService.getBible(bibleCode);
        if (bible == null) {
            return Response.status(Status.NOT_FOUND).build();
        }

        // Is the user's locale supported? If not, present choices to user.
        // Use the first book to test the locale.
        // There should never be a situation where the locale isn't present
        // for some of the books -- it should be all or nothing.
        Book bookTest = bible.getBooks().get(0);
        Locale userLocale;
        if (locale != null) {
            if (!bookTest.getBookType().supportsLocale(locale)) {
                return ResponseUtils.multipleChoices(
                    bible.getLocale(),
                    bookTest.getBookType().locales(),
                    multipleChoicesRedirect ? uriInfo.getBaseUriBuilder().path(SUB_PATH_BOOKS) : null,
                    bibleCode);
            }
            userLocale = locale;
        } else {
            userLocale = bible.getLocale();
        }

        // Prepare response
        LocaleContextHolder.setLocale(userLocale);
        List<BookBean> beans = new LinkedList<>();
        for (Book book : bible.getBooks()) {
            beans.add(conversionService.convert(book, BookBean.class));
        }

        // Send response
        return Response.ok().entity(beans).build();
    }

    @PostConstruct
    private void resolveSpringBeans() {
        conversionService = springBean(ConversionService.class);
    }

}
