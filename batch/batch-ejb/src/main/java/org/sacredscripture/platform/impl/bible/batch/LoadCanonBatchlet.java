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

import org.sacredscripture.platform.bible.service.AddBookTypeGroupRequest;
import org.sacredscripture.platform.bible.service.AddBookTypeRequest;
import org.sacredscripture.platform.bible.service.BibleMaintenanceService;
import org.sacredscripture.platform.xml.canon.XmlBookType;
import org.sacredscripture.platform.xml.canon.XmlCanon;
import org.sacredscripture.platform.xml.canon.XmlGroupType;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.batch.api.Batchlet;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.context.JobContext;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;

@Dependent
@Named("LoadCanonBatchlet")
public class LoadCanonBatchlet implements Batchlet {

    /**
     * Batch parameter specifying the XML document path.
     */
    public static final String PARAMETER_DOC_PATH = "docPath";

    @Inject
    JobContext jobContext;

    @EJB
    private BibleMaintenanceService service;

    private void createBookType(XmlBookType b, XmlGroupType group) {
        AddBookTypeRequest req = new AddBookTypeRequest();
        req.setCode(b.getCode().value());
        req.setGroupCode(group.getCode());
        service.add(req);
    }

    private void createGroup(List<XmlGroupType> groups, String parentCode) {
        for (XmlGroupType g : groups) {
            AddBookTypeGroupRequest req = new AddBookTypeGroupRequest();
            req.setCode(g.getCode());
            req.setParentCode(parentCode);
            service.add(req);

            createGroup(g.getGroup(), g.getCode());

            for (XmlBookType xmlBookType : g.getBook()) {
                createBookType(xmlBookType, g);
            }
        }
    }

    @Override
    public String process() throws Exception {
        Properties params = BatchRuntime.getJobOperator().getParameters(jobContext.getExecutionId());
        File f = new File(params.getProperty(PARAMETER_DOC_PATH));
        JAXBContext jc = JAXBContext.newInstance(XmlCanon.class);
        XmlCanon canon = (XmlCanon) jc.createUnmarshaller().unmarshal(f);
        createGroup(canon.getGroup(), null);
        return "SUCCESS";
    }

    @Override
    public void stop() throws Exception {
        // TODO
    }

}
