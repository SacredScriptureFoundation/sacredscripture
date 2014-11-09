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

import org.sacredscripture.platform.bible.service.BibleMaintenanceService;
import org.sacredscripture.platform.bible.service.SaveBookTypeGroupLocalizationRequest;
import org.sacredscripture.platform.bible.service.SaveBookTypeLocalizationRequest;
import org.sacredscripture.platform.xml.nls.XmlBookType;
import org.sacredscripture.platform.xml.nls.XmlBookTypeLocalization;
import org.sacredscripture.platform.xml.nls.XmlGroupLocalizationType;
import org.sacredscripture.platform.xml.nls.XmlGroupType;
import org.sacredscripture.platform.xml.nls.XmlNls;

import java.io.File;
import java.util.Arrays;
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

/**
 * This class is the batchlet that populates the localizations from XML.
 *
 * @author Paul Benedict
 * @see XmlNls
 * @since Sacred Scripture Platform 1.0
 */
@Dependent
@Named("LoadLocalizationsBatchlet")
public class LoadLocalizationsBatchlet extends AbstractBatchlet {

    /**
     * Batch parameter specifying the XML document path.
     */
    public static final String PARAMETER_DOC_PATH = "docPath";

    /**
     * Batch parameter specifying the only language to process. If this is
     * absent, all languages are processed.
     */
    public static final String PARAMETER_LANG_CODE = "langCode";

    @Inject
    JobContext jobContext;

    @EJB
    private BibleMaintenanceService service;

    @Override
    public String process() throws Exception {
        Properties params = BatchRuntime.getJobOperator().getParameters(jobContext.getExecutionId());
        File f = new File(params.getProperty(PARAMETER_DOC_PATH));
        String langCode = params.getProperty(PARAMETER_LANG_CODE);

        JAXBContext jc = JAXBContext.newInstance(XmlNls.class);
        XmlNls nls = (XmlNls) jc.createUnmarshaller().unmarshal(f);
        process(nls, langCode);

        return "SUCCESS";
    }

    private void process(XmlNls root, String langCode) {
        for (XmlGroupType group : root.getGroups()) {
            processGroup(group, langCode);
        }
        for (XmlBookType book : root.getBooks()) {
            processBook(book, langCode);
        }
    }

    private void processBook(XmlBookType book, String langCode) {
        for (XmlBookTypeLocalization loc : book.getLocalizations()) {
            if (langCode == null || langCode.equals(loc.getLang())) {
                SaveBookTypeLocalizationRequest req = new SaveBookTypeLocalizationRequest();
                req.setCode(book.getCode().value());
                req.setLocale(Locale.forLanguageTag(loc.getLang()));
                req.setName(loc.getName());
                req.setTitle(loc.getTitle());
                req.setAbbreviations(Arrays.asList(loc.getAbbreviations().split(",")));
                service.save(req);
            }
        }
    }

    private void processGroup(XmlGroupType group, String langCode) {
        for (XmlGroupLocalizationType loc : group.getLocalizations()) {
            if (langCode == null || langCode.equals(loc.getLang())) {
                SaveBookTypeGroupLocalizationRequest req = new SaveBookTypeGroupLocalizationRequest();
                req.setCode(group.getCode());
                req.setLocale(Locale.forLanguageTag(loc.getLang()));
                req.setName(loc.getName());
                service.save(req);
            }
        }
    }

}
