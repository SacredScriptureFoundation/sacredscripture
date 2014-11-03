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

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.batch.runtime.BatchRuntime;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * This class is the stock implementation of {@link XmlBibleBatchService}.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
// EJB
@Singleton
@Startup
public class XmlBibleBatchServiceImpl implements XmlBibleBatchService {

    @Override
    public void loadCanon(String docPath) {
        Properties props = new Properties();
        props.setProperty(LoadCanonBatchlet.PARAMETER_DOC_PATH, docPath);
        BatchRuntime.getJobOperator().start("load-canon", props);
    }

    @PostConstruct
    private void shit() {
        loadCanon("c:/Users/Paul/git/sacredscripture/src/xml/canon.xml");
    }

}
