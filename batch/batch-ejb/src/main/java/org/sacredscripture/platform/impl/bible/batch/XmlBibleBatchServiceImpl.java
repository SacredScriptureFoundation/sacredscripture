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

import org.sacredscripture.platform.bible.service.XmlBibleBatchService;

import java.util.Objects;
import java.util.Properties;

import javax.batch.runtime.BatchRuntime;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * This class is the stock implementation of {@link XmlBibleBatchService}.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
@Singleton
@Startup
public class XmlBibleBatchServiceImpl implements XmlBibleBatchService {

    private static final Logger log = LogManager.getLogger(XmlBibleBatchServiceImpl.class);
    private static final String JOB_NAME_LOAD_BIBLE = "load-bible";
    private static final String JOB_NAME_LOAD_CANON = "load-canon";
    private static final String JOB_NAME_LOAD_LOCALIZATIONS = "load-localizations";
    private static final String LOG_MSG_STARTING_JOB = "Starting batch job \"%s\"";

    @Override
    public void loadBible(String docPath) {
        Objects.requireNonNull(docPath);

        // Prepare job parameters
        Properties props = new Properties();
        props.setProperty(LoadBibleBatchlet.PARAMETER_DOC_PATH, docPath);

        // Start job
        log.info(String.format(LOG_MSG_STARTING_JOB, JOB_NAME_LOAD_BIBLE));
        BatchRuntime.getJobOperator().start(JOB_NAME_LOAD_BIBLE, props);
    }

    @Override
    public void loadCanon(String docPath) {
        Objects.requireNonNull(docPath);

        // Prepare job parameters
        Properties props = new Properties();
        props.setProperty(LoadCanonBatchlet.PARAMETER_DOC_PATH, docPath);

        // Start job
        log.info(String.format(LOG_MSG_STARTING_JOB, JOB_NAME_LOAD_CANON));
        BatchRuntime.getJobOperator().start(JOB_NAME_LOAD_CANON, props);
    }

    @Override
    public void loadLocalizations(String docPath, String langCode) {
        Objects.requireNonNull(docPath);

        // Prepare job parameters
        Properties props = new Properties();
        props.setProperty(LoadLocalizationsBatchlet.PARAMETER_DOC_PATH, docPath);
        if (langCode != null) {
            props.setProperty(LoadLocalizationsBatchlet.PARAMETER_LANG_CODE, langCode);
        }

        // Start job
        log.info(String.format(LOG_MSG_STARTING_JOB, JOB_NAME_LOAD_LOCALIZATIONS));
        BatchRuntime.getJobOperator().start(JOB_NAME_LOAD_LOCALIZATIONS, props);
    }

}
