/*
 * Copyright (c) 2015, 2016 Sacred Scripture Foundation.
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
import com.sacredscripture.platform.ws.api.rest.v1.ChapterBean;
import com.sacredscripture.platform.ws.api.rest.v1.LinkRelation;
import com.sacredscripture.platform.ws.api.rest.v1.VerseBean;

import org.sacredscripture.platform.bible.Bible;
import org.sacredscripture.platform.bible.Book;
import org.sacredscripture.platform.bible.Chapter;
import org.sacredscripture.platform.bible.Content;
import org.sacredscripture.platform.bible.Verse;
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

                // All bibles
                String allBiblesHref = getRootResourceBuilder(uriInfo).build().toString();
                bibleBean.addLink(allBiblesHref, LinkRelation.COLLECTION.rel());

                // Books
                String booksHref = getRootResourceBuilder(uriInfo).path(SUB_PATH_BOOKS).build(bible.getPublicId()).toString();
                bibleBean.addLink(booksHref, "x-books");
            } else {
                bibleBean.addLink(makeHref(bible, locale), LinkRelation.ABOUT.rel());
            }
            return bibleBean;
        }

        private String makeHref(Bible bible, Locale locale) {
            URI uri;
            UriBuilder builder = getRootResourceBuilder(uriInfo).path(SUB_PATH_BIBLE);
            if (locale != null) {
                uri = localizedUri(builder, locale, bible.getPublicId());
            } else {
                uri = builder.build(bible.getPublicId());
            }
            return uri.toString();
        }
    }

    /**
     * This private inner class constructs a {@link BookBean} view.
     */
    private class BookBeanBuilder {
        public Locale locale;
        public boolean self;

        public BookBean build(Book book) {
            BookBean bookBean = conversionService.convert(book, BookBean.class);
            if (self) {
                bookBean.addLink(linkHref(book, locale), LinkRelation.SELF, linkTitle(book));

                // Alternate languages
                for (Locale altLocale : book.getBookType().locales()) {
                    if (!altLocale.equals(locale)) {
                        bookBean.addLink(linkHref(book, altLocale), LinkRelation.ALTERNATE.rel(), altLocale, linkTitle(book));
                    }
                }

                // Previous book
                Book prevBook = book.previous();
                if (prevBook != null) {
                    bookBean.addLink(linkHref(prevBook, locale), LinkRelation.PREV, linkTitle(book));
                }

                // Next book
                Book nextBook = book.next();
                if (nextBook != null) {
                    bookBean.addLink(linkHref(nextBook, locale), LinkRelation.NEXT, linkTitle(book));
                }

                // Up to Bible
                Bible bible = book.getBible();
                BibleBeanBuilder bb = new BibleBeanBuilder();
                bookBean.addLink(bb.makeHref(bible, locale), LinkRelation.UP, linkTitle(book));

                // Chapters
                List<Chapter> chapters = queryService.getChapters(bible.getPublicId(), book.getOrder());
                for (Chapter chapter : chapters) {
                    ChapterBeanBuilder cb = new ChapterBeanBuilder();
                    cb.locale = locale;
                    ChapterBean chapterBean = cb.build(chapter);
                    if (bookBean.getChapters() == null) {
                        bookBean.setChapters(new LinkedList<ChapterBean>());
                    }
                    bookBean.getChapters().add(chapterBean);
                }
            } else {
                bookBean.addLink(linkHref(book, locale), LinkRelation.ABOUT, linkTitle(book));
            }
            return bookBean;
        }

        private String linkHref(Book book, Locale locale) {
            URI uri;
            UriBuilder builder = getRootResourceBuilder(uriInfo).path(SUB_PATH_BOOK);
            if (locale != null) {
                uri = localizedUri(builder, locale, book.getBible().getPublicId(), book.getOrder());
            } else {
                uri = builder.build(book.getBible().getPublicId(), book.getOrder());
            }
            return uri.toString();
        }

        private String linkTitle(Book book) {
            StringBuilder buf = new StringBuilder();
            buf.append(book.getName());
            buf.append(" (");
            buf.append(book.getBible().getAbbreviation());
            buf.append(")");
            return buf.toString();
        }
    }

    private class ChapterBeanBuilder {
        public Locale locale;
        public boolean self;

        public ChapterBean build(Chapter chapter) {
            ChapterBean chapterBean = conversionService.convert(chapter, ChapterBean.class);
            if (self) {
                chapterBean.addLink(linkHref(chapter, locale), LinkRelation.SELF, linkTitle(chapter));

                // Verses
                for (Verse verse : chapter.getVerses()) {
                    VerseBeanBuilder bb = new VerseBeanBuilder();
                    bb.locale = chapter.getBook().getBible().getLocale();
                    chapterBean.addContent(bb.build(verse));
                }

                // Up to book
                BookBeanBuilder bb = new BookBeanBuilder();
                Book book = chapter.getBook();
                chapterBean.addLink(bb.linkHref(book, locale), LinkRelation.UP, bb.linkTitle(book));
            } else {
                chapterBean.addLink(linkHref(chapter, locale), LinkRelation.ABOUT, linkTitle(chapter));
            }
            return chapterBean;
        }

        private String linkHref(Chapter chapter, Locale locale) {
            URI uri;
            UriBuilder builder = getRootResourceBuilder(uriInfo).path(SUB_PATH_CONTENT);
            if (locale != null) {
                uri = localizedUri(builder, locale, chapter.getPublicId());
            } else {
                uri = builder.build(chapter.getPublicId());
            }
            return uri.toString();
        }

        private String linkTitle(Chapter chapter) {
            StringBuilder buf = new StringBuilder();
            buf.append(chapter.getBook().getName());
            buf.append(" ");
            buf.append(chapter.getName());
            buf.append(" (");
            buf.append(chapter.getBook().getBible().getAbbreviation());
            buf.append(")");
            return buf.toString();
        }
    }

    private class VerseBeanBuilder {
        public Locale locale;
        public boolean self;

        public VerseBean build(Verse verse) {
            VerseBean verseBean = conversionService.convert(verse, VerseBean.class);
            if (self) {
                verseBean.addLink(linkHref(verse, locale), LinkRelation.SELF, linkTitle(verse));

                // Previous verse
                Verse previous = verse.getPrevious();
                if (previous != null) {
                    verseBean.addLink(linkHref(previous, locale), LinkRelation.PREV, linkTitle(previous));
                }

                // Next verse
                Verse next = verse.getNext();
                if (next != null) {
                    verseBean.addLink(linkHref(next, locale), LinkRelation.NEXT, linkTitle(next));
                }

                // Up to chapter
                ChapterBeanBuilder cb = new ChapterBeanBuilder();
                Chapter chapter = verse.getChapter();
                verseBean.addLink(cb.linkHref(chapter, locale), LinkRelation.UP, cb.linkTitle(chapter));

                // Embedded chapter
                verseBean.addEmbedded("chapter", cb.build(chapter));

                // Embedded book
                BookBeanBuilder bb = new BookBeanBuilder();
                verseBean.addEmbedded("book", bb.build(chapter.getBook()));

                // Embedded bible
                BibleBeanBuilder bb2 = new BibleBeanBuilder();
                verseBean.addEmbedded("bible", bb2.build(chapter.getBook().getBible()));
            } else {
                verseBean.addLink(linkHref(verse, locale), LinkRelation.ABOUT, linkTitle(verse));
            }
            return verseBean;
        }

        private String linkHref(Verse verse, Locale locale) {
            URI uri;
            UriBuilder builder = getRootResourceBuilder(uriInfo).path(SUB_PATH_CONTENT);
            if (locale != null) {
                uri = localizedUri(builder, locale, verse.getPublicId());
            } else {
                uri = builder.build(verse.getPublicId());
            }
            return uri.toString();
        }

        private String linkTitle(Verse verse) {
            StringBuilder buf = new StringBuilder();
            buf.append(verse.getBook().getName());
            buf.append(" ");
            buf.append(verse.getChapter().getName());
            buf.append(":");
            buf.append(verse.getName());
            buf.append(" (");
            buf.append(verse.getBook().getBible().getAbbreviation());
            buf.append(")");
            return buf.toString();
        }
    }

    // Paths
    public static final String ROOT_PATH = "/rest/v1/bibles";
    public static final String SUB_PATH_BIBLE = "/{bible}";
    public static final String SUB_PATH_BOOK = "/{bible}/books/{book}";
    public static final String SUB_PATH_BOOKS = "/{bible}/books";
    public static final String SUB_PATH_CONTENT = "/content/{id}";

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
            @PathParam("bible") String bibleId,
            @QueryParam("bg") int bookGroupDepth,
            @QueryParam("lang") Locale locale,
            @QueryParam("mcr") boolean multipleChoicesRedirect) {
        // Does the bible exist?
        Bible bible = queryService.getBibleByPublicId(bibleId);
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
                    bibleId);
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
        // Query for all bibles
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
            @PathParam("bible") String bibleId,
            @PathParam("book") int bookIndex,
            @QueryParam("lang") Locale locale,
            @QueryParam("mcr") boolean multipleChoicesRedirect) {
        // Does the bible exist?
        Bible bible = queryService.getBibleByPublicId(bibleId);
        if (bible == null) {
            return Response.status(Status.NOT_FOUND).build();
        }

        // Does the book exist?
        List<Book> books = bible.getBooks();
        Book book;
        try {
            book = books.get(bookIndex);
        } catch (IndexOutOfBoundsException e) {
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
                    bibleId,
                    bookIndex);
            }
            userLocale = locale;
        } else {
            userLocale = bible.getLocale();
        }
        LocaleContextHolder.setLocale(userLocale);

        // Build representation
        BookBeanBuilder builder = new BookBeanBuilder();
        builder.locale = locale;
        builder.self = true;
        BookBean bookBean = builder.build(book);

        // Send response
        return Response.ok().entity(bookBean).build();
    }

    @Path(SUB_PATH_BOOKS)
    @GET
    public Response getBooks(
            @PathParam("bible") String bibleId,
            @QueryParam("lang") Locale locale,
            @QueryParam("mcr") boolean multipleChoicesRedirect) {
        // Does the bible exist?
        Bible bible = queryService.getBibleByPublicId(bibleId);
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
                    bibleId);
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
            BookBean bookBean = builder.build(book);
            beans.add(bookBean);
        }

        // Send response
        return Response.ok().entity(beans).build();
    }

    @Path(SUB_PATH_CONTENT)
    @GET
    public Response getContent(@PathParam("id") String contentId) {
        Content content = queryService.getContent(contentId);
        if (content == null) {
            return Response.status(Status.NOT_FOUND).build();
        }

        Object entity;
        if (content instanceof Chapter) {
            Chapter chapter = (Chapter) content;
            ChapterBeanBuilder bb = new ChapterBeanBuilder();
            bb.locale = chapter.getBook().getBible().getLocale();
            bb.self = true;
            entity = bb.build(chapter);
        } else if (content instanceof Verse) {
            Verse verse = (Verse) content;
            VerseBeanBuilder bb = new VerseBeanBuilder();
            bb.locale = verse.getChapter().getBook().getBible().getLocale();
            bb.self = true;
            entity = bb.build(verse);
        } else {
            // TODO what really to do here?
            return Response.status(Status.NOT_FOUND).build();
        }

        return Response.ok().entity(entity).build();
    }

    @PostConstruct
    private void resolveSpringBeans() {
        conversionService = springBean(ConversionService.class);
    }

}
