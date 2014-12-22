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
package org.sacredscripture.platform.impl.bible.batch;

import static javax.xml.stream.XMLStreamConstants.END_ELEMENT;
import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;

import org.sacredscripture.platform.bible.Chapter;
import org.sacredscripture.platform.bible.service.AddBookRequest;
import org.sacredscripture.platform.bible.service.AddChapterRequest;
import org.sacredscripture.platform.bible.service.AddVerseRequest;
import org.sacredscripture.platform.bible.service.BibleMaintenanceService;
import org.sacredscripture.platform.bible.service.SaveBibleRequest;
import org.sacredscripture.platform.xml.bible.XmlBibleType;
import org.sacredscripture.platform.xml.bible.XmlBookCodeType;
import org.sacredscripture.platform.xml.bible.XmlBookType;
import org.sacredscripture.platform.xml.bible.XmlChapterType;
import org.sacredscripture.platform.xml.bible.XmlVerseType;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Properties;

import javax.batch.runtime.BatchRuntime;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

/**
 * This class is the batchlet that populates a bible from XML.
 * <p>
 * Logging: <br/>
 * debug - processing details <br/>
 * trace - unmarshalling and inner logic details
 *
 * @author Paul Benedict
 * @see XmlBibleType
 * @since Sacred Scripture Platform 1.0
 */
@Dependent
@Named("LoadBibleBatchlet")
public class LoadBibleBatchlet extends BaseBatchlet {

    // Log messages
    private static final String LOG_MSG_BIBLE_UM = "Unmarshalling bible...";
    private static final String LOG_MSG_BIBLE_UM_ERR = "Failure unmarshalling bible";
    private static final String LOG_MSG_BIBLE_UM_OK = "Success unmarshalling bible [code={}]";
    private static final String LOG_MSG_BOOK_PROCESS = "Processing book [code={}]";
    private static final String LOG_MSG_BOOK_UM = "Unmarshalling book...";
    private static final String LOG_MSG_BOOK_UM_ERR = "Failure unmarshalling book";
    private static final String LOG_MSG_BOOK_UM_OK = "Success unmarshalling book [code={}]";
    private static final String LOG_MSG_CHAPTER_AUTOCODE = "Auto-generating chapter code";
    private static final String LOG_MSG_CHAPTER_PROCESS = "Processing chapter [name={}]";
    private static final String LOG_MSG_CHAPTER_UM = "Unmarshalling chapter...";
    private static final String LOG_MSG_CHAPTER_UM_ERR = "Failure unmarshalling chapter";
    private static final String LOG_MSG_CHAPTER_UM_OK = "Success unmarshalling chapter [name={}]";
    private static final String LOG_MSG_VERSE_AUTOCODE = "Auto-generating verse code";
    private static final String LOG_MSG_VERSE_SAVING = "Adding verse to system";
    private static final String LOG_MSG_VERSE_PROCESS = "Processing verse [name={}]";
    private static final String LOG_MSG_VERSE_PROCESS_ERR = "Failure processing verse [name={}]";
    private static final String LOG_MSG_VERSE_PROCESS_OK = "Success processing verse";

    /**
     * Batch parameter specifying the XML document path.
     */
    public static final String PARAMETER_DOC_PATH = "docPath";

    /**
     * Pattern used to identify "normal" chapter and verse numbering.
     */
    private static final String NATURAL_NUMBER_PATTERN = "^[1-9][0-9]*$";

    @EJB
    private BibleMaintenanceService service;

    @Override
    public String process() throws Exception {
        Properties params = BatchRuntime.getJobOperator().getParameters(jobContext.getExecutionId());
        Path docPath = FileSystems.getDefault().getPath(params.getProperty(PARAMETER_DOC_PATH));
        File f = new File(docPath.toString());

        XMLInputFactory xif = XMLInputFactory.newFactory();
        XMLStreamReader xsr = null;
        try {
            xsr = xif.createXMLStreamReader(new StreamSource(f));
            process(xsr, docPath);
        } catch (Exception e) {
            log.error("Error processing bible", e);
            throw e;
        } finally {
            if (xsr != null) {
                xsr.close();
            }
        }

        return "SUCCESS";
    }

    private void process(XMLStreamReader xsr, Path bibleDocPath) throws Exception {
        XmlBibleType bible = unmarshallBible(xsr);
        SaveBibleRequest req = new SaveBibleRequest();
        req.setAbbreviation(bible.getAbbreviation());
        req.setCode(bible.getCode());
        req.setCopyrightNotice(bible.getCopyright());
        req.setLicense(bible.getLicense());
        req.setLocale(Locale.forLanguageTag(bible.getLang()));
        req.setName(bible.getName());
        req.setRightToLeftReading(false); // FIXME
        req.setTitle(bible.getTitle());
        service.save(req);

        log.trace("Processing books");
        for (;;) {
            xsr.nextTag();
            switch (xsr.getLocalName()) {
            case "include":
                processBookInclude(bible, bibleDocPath, xsr.getAttributeValue(null, "href"));
                xsr.nextTag();
                xsr.require(END_ELEMENT, null, "include");
                break;
            case "book":
                processBook(bible, xsr);
                break;
            case "bible":
                // End of bible expected
                xsr.require(END_ELEMENT, null, "bible");
                return;
            default:
                throw new IllegalStateException("Unexpected element: " + xsr.getLocalName());
            }
        }

    }

    private void processBook(XmlBibleType bible, XMLStreamReader xsr) throws Exception {
        XmlBookType book = unmarshallBook(xsr);
        log.debug(LOG_MSG_BOOK_PROCESS, book.getCode().value());
        try {
            AddBookRequest req = new AddBookRequest();
            req.setBibleCode(bible.getCode());
            req.setBookTypeCode(book.getCode().value());
            service.add(req);

            // Process each chapter found
            xsr.nextTag();
            do {
                processChapter(xsr, bible, book);
            } while (xsr.next() == START_ELEMENT && "chapter".equals(xsr.getLocalName()));

            // End of book expected
            xsr.require(END_ELEMENT, null, "book");
        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * When the main XML document specifies the book as an included document,
     * this method will create and manage a new stream to that book.
     *
     * @param xmlBible the parent bible
     * @param bibleDocPath path of the bible document
     * @param bookDocPath the book path (could be relative)
     * @throws Exception if a processing error occurs
     */
    private void processBookInclude(XmlBibleType xmlBible, Path bibleDocPath, String bookDocPath) throws Exception {
        XMLInputFactory xif = XMLInputFactory.newFactory();
        XMLStreamReader xsr = null;
        try {
            xsr = xif.createXMLStreamReader(new StreamSource(bibleDocPath.resolveSibling(bookDocPath).toString()));
            processBook(xmlBible, xsr);
        } finally {
            if (xsr != null) {
                xsr.close();
            }
        }
    }

    private void processChapter(XMLStreamReader xsr, XmlBibleType xmlBible, XmlBookType xmlBook) throws Exception {
        XmlChapterType xmlChapter = unmarshallChapter(xsr);
        log.debug(LOG_MSG_CHAPTER_PROCESS, xmlChapter.getName());

        // Auto-generate code if none is explicitly specified and the chapter
        // name is a natural number
        String code = xmlChapter.getCode();
        if ((code == null) && xmlChapter.getName().matches(NATURAL_NUMBER_PATTERN)) {
            log.trace(LOG_MSG_CHAPTER_AUTOCODE);
            StringBuilder sb = new StringBuilder();
            sb.append(xmlBook.getCode().value());
            sb.append(":");
            sb.append(xmlChapter.getName());
            code = sb.toString();
        }

        // Add the chapter to the system
        AddChapterRequest req = new AddChapterRequest();
        req.setBibleCode(xmlBible.getCode());
        req.setBookTypeCode(xmlBook.getCode().value());
        req.setCode(code);
        req.setName(xmlChapter.getName());
        Chapter chapter = service.add(req);

        // Process each verse
        for (XmlVerseType xmlVerse : xmlChapter.getVerses()) {
            processVerse(xmlVerse, chapter);
        }
    }

    private void processVerse(XmlVerseType xmlVerse, Chapter chapter) {
        log.debug(LOG_MSG_VERSE_PROCESS, xmlVerse.getName());
        try {
            // Auto-generate a code if no code is explicitly specified and the
            // verse name is a natural number
            String code = xmlVerse.getCode();
            if ((code == null) && xmlVerse.getName().matches(NATURAL_NUMBER_PATTERN)) {
                log.trace(LOG_MSG_VERSE_AUTOCODE);
                StringBuilder sb = new StringBuilder();
                sb.append(chapter.getBook().getBookType().getCode());
                sb.append(":");
                sb.append(chapter.getName());
                sb.append(":");
                sb.append(xmlVerse.getName());
                code = sb.toString();
            }

            // Save verse
            log.trace(LOG_MSG_VERSE_SAVING);
            AddVerseRequest req = new AddVerseRequest();
            req.setAltName(xmlVerse.getAltName());
            req.setChapterId(chapter.getId());
            req.setCode(code);
            req.setName(xmlVerse.getName());
            req.setOmitted(xmlVerse.isDeprecated() != null ? xmlVerse.isDeprecated() : false);
            req.setText(xmlVerse.getContent());
            service.add(req);

            log.trace(LOG_MSG_VERSE_PROCESS_OK);
        } catch (Exception e) {
            log.error(LOG_MSG_VERSE_PROCESS_ERR, xmlVerse.getName());
            throw e;
        }
    }

    /**
     * Creates a new {@link XmlBibleType} out of the XML stream. Because of the
     * streaming, no books will be populated in the object.
     *
     * @param xsr the XML stream reader
     * @return the bible
     * @throws Exception if a reading error occurs
     */
    private XmlBibleType unmarshallBible(XMLStreamReader xsr) throws Exception {
        log.trace(LOG_MSG_BIBLE_UM);
        try {
            XmlBibleType xmlBible = new XmlBibleType();

            xsr.nextTag();
            xsr.require(START_ELEMENT, null, "bible");
            xmlBible.setCode(xsr.getAttributeValue(null, "code"));

            xsr.nextTag();
            xsr.require(START_ELEMENT, null, "lang");
            xmlBible.setLang(xsr.getElementText());

            xsr.nextTag();
            xsr.require(START_ELEMENT, null, "name");
            xmlBible.setName(xsr.getElementText());

            xsr.nextTag();
            xsr.require(START_ELEMENT, null, "title");
            xmlBible.setTitle(xsr.getElementText());

            xsr.nextTag();
            xsr.require(START_ELEMENT, null, "abbreviation");
            xmlBible.setAbbreviation(xsr.getElementText());

            xsr.nextTag();
            xsr.require(START_ELEMENT, null, "copyright");
            xmlBible.setCopyright(xsr.getElementText());

            xsr.nextTag();
            xsr.require(START_ELEMENT, null, "license");
            xmlBible.setLicense(xsr.getElementText());

            log.trace(LOG_MSG_BIBLE_UM_OK, xmlBible.getCode());
            return xmlBible;
        } catch (Exception e) {
            log.error(LOG_MSG_BIBLE_UM_ERR);
            throw e;
        }
    }

    private XmlBookType unmarshallBook(XMLStreamReader xsr) throws Exception {
        log.trace(LOG_MSG_BOOK_UM);
        try {
            xsr.nextTag();
            xsr.require(START_ELEMENT, null, "book");
            XmlBookType xmlBook = new XmlBookType();
            xmlBook.setCode(XmlBookCodeType.fromValue(xsr.getAttributeValue(null, "code")));
            log.trace(LOG_MSG_BOOK_UM_OK, xmlBook.getCode().value());
            return xmlBook;
        } catch (Exception e) {
            log.error(LOG_MSG_BOOK_UM_ERR);
            throw e;
        }
    }

    /**
     * Creates a new {@link XmlChapterType} out of the XML stream. The entire
     * object is materialized because a chapter (and all its verses) should fit
     * into memory just fine.
     *
     * @param xsr the XML stream reader
     * @return the chapter
     * @throws Exception if a reading error occurs
     */
    private XmlChapterType unmarshallChapter(XMLStreamReader xsr) throws Exception {
        log.trace(LOG_MSG_CHAPTER_UM);
        try {
            Unmarshaller um = JAXBContext.newInstance(XmlChapterType.class).createUnmarshaller();
            XmlChapterType xmlChapter = um.unmarshal(xsr, XmlChapterType.class).getValue();
            log.trace(LOG_MSG_CHAPTER_UM_OK, xmlChapter.getName());
            return xmlChapter;
        } catch (Exception e) {
            log.error(LOG_MSG_CHAPTER_UM_ERR);
            throw e;
        }
    }

}
