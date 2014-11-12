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

import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;

import org.sacredscripture.platform.bible.service.BibleMaintenanceService;
import org.sacredscripture.platform.bible.service.SaveBibleRequest;
import org.sacredscripture.platform.xml.bible.XmlBibleType;

import java.io.File;
import java.util.Locale;
import java.util.Properties;

import javax.batch.api.AbstractBatchlet;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.context.JobContext;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
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
        File f = new File(params.getProperty(PARAMETER_DOC_PATH));

        XMLInputFactory xif = XMLInputFactory.newFactory();
        XMLStreamReader xsr = xif.createXMLStreamReader(new StreamSource(f));
        process(xsr);

        return "SUCCESS";
    }

    private void process(XMLStreamReader xsr) throws XMLStreamException {
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
    }

    /**
     * Creates a new {@link XmlBibleType} out of the XML stream. Because of the
     * streaming, no books will be populated in the object.
     *
     * @param xsr the XML stream reader
     * @return the bible
     * @throws XMLStreamException if a reading error occurs
     */
    private XmlBibleType unmarshallBible(XMLStreamReader xsr) throws XMLStreamException {
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

}
