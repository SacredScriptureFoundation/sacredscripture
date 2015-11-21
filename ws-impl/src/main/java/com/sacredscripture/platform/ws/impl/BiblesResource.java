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

import static com.sacredscripture.platform.ws.impl.ResponseUtils.localizedUri;
import static com.sacredscripture.platform.ws.impl.ResponseUtils.multipleChoices;

import com.sacredscripture.platform.ws.api.rest.v1.BibleBean;
import com.sacredscripture.platform.ws.api.rest.v1.BookBean;
import com.sacredscripture.platform.ws.api.rest.v1.LinkRelation;

import org.sacredscripture.platform.bible.Bible;
import org.sacredscripture.platform.bible.Book;
import org.sacredscripture.platform.bible.service.BibleQueryService;

import org.sacredscripturefoundation.commons.locale.LocaleContextHolder;

import java.net.URI;
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
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.springframework.core.convert.ConversionService;

@Path(BiblesResource.ROOT_PATH)
@Produces("application/json")
public class BiblesResource extends AbstractSpringAwareResource {

    /**
     * This private inner class constructs a {@link BibleBean} view.
     */
    // NOTE if both views get too complex, get rid of "self" and create
    // subclasses for build()
    private class BibleBeanBuilder {
        public Locale locale;
        public boolean self;

        public BibleBean build(Bible bible) {
            BibleBean bibleBean = conversionService.convert(bible, BibleBean.class);
            if (self) {
                bibleBean.addLink(makeHref(bible, locale), LinkRelation.SELF.rel());

                // Alternate languages
                for (Locale altLocale : bible.getLocalizedContents().keySet()) {
                    if (!altLocale.equals(locale)) {
                        bibleBean.addLink(makeHref(bible, altLocale), LinkRelation.ALTERNATE.rel(), altLocale);
                    }
                }
            } else {
                bibleBean.addLink(makeHref(bible, locale), LinkRelation.ABOUT.rel());
            }
            return bibleBean;
        }

        private String makeHref(Bible bible, Locale locale) {
            URI uri;
            UriBuilder builder = getRootResourceBuilder(uriInfo).path(SUB_PATH_BIBLE);
            if (locale != null) {
                uri = localizedUri(builder, locale, bible.getCode());
            } else {
                uri = builder.build(bible.getCode());
            }
            return uri.toString();
        }
    }

    /**
     * This private inner class constructs a {@link BookBean} view.
     */
    // NOTE if both views get too complex, get rid of "self" and create
    // subclasses for build()
    private class BookBeanBuilder {
        public Locale locale;
        public Bible bible;
        public boolean self;

        public BookBean build(Book book) {
            BookBean bookBean = conversionService.convert(book, BookBean.class);
            if (self) {
                bookBean.addLink(makeHref(book, locale), LinkRelation.SELF.rel());

                // Alternate languages
                for (Locale altLocale : book.getBookType().locales()) {
                    if (!altLocale.equals(locale)) {
                        bookBean.addLink(makeHref(book, altLocale), LinkRelation.ALTERNATE.rel(), altLocale);
                    }
                }

                // Previous book
                Book prevBook = book.previous();
                if (prevBook != null) {
                    bookBean.addLink(makeHref(prevBook, locale), LinkRelation.PREV.rel());
                }

                // Next book
                Book nextBook = book.next();
                if (nextBook != null) {
                    bookBean.addLink(makeHref(nextBook, locale), LinkRelation.NEXT.rel());
                }

                // Bible
                BibleBeanBuilder bb = new BibleBeanBuilder();
                bookBean.addLink(bb.makeHref(bible, locale), LinkRelation.UP.rel());
            } else {
                bookBean.addLink(makeHref(book, locale), LinkRelation.ABOUT.rel());
            }
            return bookBean;
        }

        private String makeHref(Book book, Locale locale) {
            URI uri;
            UriBuilder builder = getRootResourceBuilder(uriInfo).path(SUB_PATH_BOOK);
            if (locale != null) {
                uri = localizedUri(builder, locale, bible.getCode(), book.getBookType().getCode());
            } else {
                uri = builder.build(bible.getCode(), book.getBookType().getCode());
            }
            return uri.toString();
        }
    }

    // Paths
    public static final String ROOT_PATH = "/rest/v1/bibles";
    public static final String SUB_PATH_BIBLE = "/{bible}";
    public static final String SUB_PATH_BOOKS = "/{bible}/books";
    public static final String SUB_PATH_BOOK = "/{bible}/books/{book}";

    /**
     * Creates a new {@link UriBuilder} to the resource root of this class.
     * <p>
     * Remove when
     * <a href="https://java.net/jira/browse/JAX_RS_SPEC-519">JAX_RS_SPEC-519
     * </a> gets implemented.
     */
    private static UriBuilder getRootResourceBuilder(UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path(ROOT_PATH);
    }

    @EJB
    private BibleQueryService queryService;
    private ConversionService conversionService;
    @Context
    private UriInfo uriInfo;

    @Path(SUB_PATH_BIBLE)
    @GET
    public Response getBible(
            @PathParam("bible") String bibleCode,
            @QueryParam("bg") int bookGroupDepth,
            @QueryParam("lang") Locale locale,
            @QueryParam("mcr") boolean multipleChoicesRedirect) {
        // Does the bible exist?
        Bible bible = queryService.getBible(bibleCode);
        if (bible == null) {
            return Response.status(Status.NOT_FOUND).build();
        }

        // Is the user's locale supported? If not, present choices to user
        Locale userLocale;
        if (locale != null) {
            if (!bible.supportsLocale(locale)) {
                return multipleChoices(
                    bible.getLocale(),
                    bible.locales(),
                    multipleChoicesRedirect ? uriInfo.getAbsolutePathBuilder() : null,
                    bibleCode);
            }
            userLocale = locale;
        } else {
            userLocale = bible.getLocale();
        }
        LocaleContextHolder.setLocale(userLocale);

        // Build representation
        BibleBeanBuilder builder = new BibleBeanBuilder();
        builder.locale = locale;
        builder.self = true;
        BibleBean bibleBean = builder.build(bible);

        // Send response
        return Response.ok().entity(bibleBean).build();
    }

    /**
     * Retrieves all bibles supported by the platform.
     *
     * @return the response
     */
    @GET
    public Response getBibles() {
        // Query for all bibiles
        List<Bible> bibles = queryService.getBibles(null);

        // Build representation
        List<BibleBean> bibleBeans = new LinkedList<>();
        for (Bible bible : bibles) {
            BibleBeanBuilder builder = new BibleBeanBuilder();
            BibleBean bibleBean = builder.build(bible);
            bibleBeans.add(bibleBean);
        }

        // Send response
        return Response.ok().entity(bibleBeans).build();
    }

    @Path(SUB_PATH_BOOK)
    @GET
    public Response getBook(
            @PathParam("bible") String bibleCode,
            @PathParam("book") String bookCode,
            @QueryParam("lang") Locale locale,
            @QueryParam("mcr") boolean multipleChoicesRedirect) {
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

        // Is the requested locale supported? If not, present choices to user
        Locale userLocale;
        if (locale != null) {
            if (!book.getBookType().supportsLocale(locale)) {
                return multipleChoices(
                    bible.getLocale(),
                    book.getBookType().locales(),
                    multipleChoicesRedirect ? uriInfo.getAbsolutePathBuilder() : null,
                    bibleCode,
                    bookCode);
            }
            userLocale = locale;
        } else {
            userLocale = bible.getLocale();
        }
        LocaleContextHolder.setLocale(userLocale);

        // Build representation
        BookBeanBuilder builder = new BookBeanBuilder();
        builder.locale = locale;
        builder.bible = bible;
        builder.self = true;
        BookBean bookBean = builder.build(book);

        // Send response
        return Response.ok().entity(bookBean).build();
    }

    @Path(SUB_PATH_BOOKS)
    @GET
    public Response getBooks(
            @PathParam("bible") String bibleCode,
            @QueryParam("lang") Locale locale,
            @QueryParam("mcr") boolean multipleChoicesRedirect) {
        // Does the bible exist?
        Bible bible = queryService.getBible(bibleCode);
        if (bible == null) {
            return Response.status(Status.NOT_FOUND).build();
        }

        // Is the user's locale supported? If not, present choices to user.
        // Use the first book to test the locale; there should never be a
        // situation where the locale isn't present for all of the books
        Locale userLocale;
        if (locale != null) {
            Book bookTest = bible.getBooks().get(0);
            if (!bookTest.getBookType().supportsLocale(locale)) {
                return multipleChoices(
                    bible.getLocale(),
                    bookTest.getBookType().locales(),
                    multipleChoicesRedirect ? uriInfo.getAbsolutePathBuilder() : null,
                    bibleCode);
            }
            userLocale = locale;
        } else {
            userLocale = bible.getLocale();
        }
        LocaleContextHolder.setLocale(userLocale);

        // Build representation
        List<BookBean> beans = new LinkedList<>();
        for (Book book : bible.getBooks()) {
            BookBeanBuilder builder = new BookBeanBuilder();
            builder.locale = locale;
            builder.bible = bible;
            BookBean bookBean = builder.build(book);
            beans.add(bookBean);
        }

        // Send response
        return Response.ok().entity(beans).build();
    }

    @PostConstruct
    private void resolveSpringBeans() {
        conversionService = springBean(ConversionService.class);
    }

}
