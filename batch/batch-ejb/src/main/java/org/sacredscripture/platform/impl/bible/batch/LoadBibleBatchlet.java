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

import javax.batch.api.AbstractBatchlet;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.context.JobContext;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

/**
 * This class is the batchlet that populates a bible from XML.
 *
 * @author Paul Benedict
 * @see XmlBibleType
 * @since Sacred Scripture Platform 1.0
 */
@Dependent
@Named("LoadBibleBatchlet")
public class LoadBibleBatchlet extends AbstractBatchlet {

    public static void main(String[] args) throws Exception {
        new LoadBibleBatchlet().process();
    }

    /**
     * Batch parameter specifying the XML document path.
     */
    public static final String PARAMETER_DOC_PATH = "docPath";

    @Inject
    JobContext jobContext;

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
        // service.save(req);

        xsr.nextTag();
        switch (xsr.getLocalName()) {
        case "include":
            processExternalBook(bible, bibleDocPath, xsr.getAttributeValue(null, "href"));
            break;
        case "book":
            processBook(bible, xsr);
            break;
        default:
            throw new AssertionError();
        }
    }

    private void processBook(XmlBibleType bible, XMLStreamReader xsr) throws Exception {
        // Add book
        XmlBookType book = unmarshallBook(xsr);
        AddBookRequest req = new AddBookRequest();
        req.setBibleCode(bible.getCode());
        req.setBookTypeCode(book.getCode().value());
        // service.add(req);

        // Process each chapter found
        xsr.nextTag();
        do {
            processChapter(xsr, bible, book);
        } while (xsr.next() == START_ELEMENT && "chapter".equals(xsr.getLocalName()));

        // The book should end when there are no more chapters
        xsr.require(END_ELEMENT, null, "book");
    }

    private void processChapter(XMLStreamReader xsr, XmlBibleType bible, XmlBookType book) throws Exception {
        XmlChapterType chapter = unmarshallChapter(xsr);
        AddChapterRequest req = new AddChapterRequest();
        req.setBibleCode(bible.getCode());
        req.setBookTypeCode(book.getCode().value());
        req.setName(chapter.getName());
        Chapter chapterEntity = service.add(req);

        for (XmlVerseType verse : chapter.getVerses()) {
            processVerse(verse, chapterEntity);
        }
    }

    /**
     * When the main XML document specifies the book as an included document,
     * this method will create and manage a new stream to that book.
     *
     * @param bible the parent bible
     * @param bibleDocPath path of the bible document
     * @param bookDocPath the book path (could be relative)
     * @throws Exception if a processing error occurs
     */
    private void processExternalBook(XmlBibleType bible, Path bibleDocPath, String bookDocPath) throws Exception {
        XMLInputFactory xif = XMLInputFactory.newFactory();
        XMLStreamReader xsr = null;
        try {
            xsr = xif.createXMLStreamReader(new StreamSource(bibleDocPath.resolveSibling(bookDocPath).toString()));
            processBook(bible, xsr);
        } finally {
            if (xsr != null) {
                xsr.close();
            }
        }
    }

    private void processVerse(XmlVerseType verse, Chapter chapterEntity) {
        AddVerseRequest req = new AddVerseRequest();
        req.setAltName(verse.getAltName());
        req.setChapterId(chapterEntity.getId());
        req.setCode(verse.getCode());
        req.setName(verse.getName());
        req.setOmitted(verse.isDeprecated() != null ? verse.isDeprecated() : false);
        req.setText(verse.getContent());
        service.add(req);
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
        XmlBibleType bible = new XmlBibleType();

        xsr.nextTag();
        xsr.require(START_ELEMENT, null, "bible");
        bible.setCode(xsr.getAttributeValue(null, "code"));

        xsr.nextTag();
        xsr.require(START_ELEMENT, null, "lang");
        bible.setLang(xsr.getElementText());

        xsr.nextTag();
        xsr.require(START_ELEMENT, null, "name");
        bible.setName(xsr.getElementText());

        xsr.nextTag();
        xsr.require(START_ELEMENT, null, "title");
        bible.setTitle(xsr.getElementText());

        xsr.nextTag();
        xsr.require(START_ELEMENT, null, "abbreviation");
        bible.setAbbreviation(xsr.getElementText());

        xsr.nextTag();
        xsr.require(START_ELEMENT, null, "copyright");
        bible.setCopyright(xsr.getElementText());

        xsr.nextTag();
        xsr.require(START_ELEMENT, null, "license");
        bible.setLicense(xsr.getElementText());

        return bible;
    }

    private XmlBookType unmarshallBook(XMLStreamReader xsr) throws XMLStreamException {
        xsr.nextTag();
        xsr.require(START_ELEMENT, null, "book");
        XmlBookType book = new XmlBookType();
        book.setCode(XmlBookCodeType.fromValue(xsr.getAttributeValue(null, "code")));
        return book;
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
        JAXBContext jc = JAXBContext.newInstance(XmlChapterType.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        return unmarshaller.unmarshal(xsr, XmlChapterType.class).getValue();
    }

}
