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

import javax.batch.runtime.BatchRuntime;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;

/**
 * This class is the batchlet that populates the canon from XML.
 *
 * @author Paul Benedict
 * @see XmlCanon
 * @since Sacred Scripture Platform 1.0
 */
@Dependent
@Named("LoadCanonBatchlet")
public class LoadCanonBatchlet extends BaseBatchlet {

    /**
     * Batch parameter specifying the XML document path.
     */
    public static final String PARAMETER_DOC_PATH = "docPath";

    private static final String LOG_MSG_CREATING_BOOK = "Creating book \"%s\" in group \"%s\"";
    private static final String LOG_MSG_CREATING_GROUP = "Creating group \"%s\" under parent \"%s\"";

    @EJB
    private BibleMaintenanceService service;

    private void createBookType(XmlBookType book, XmlGroupType group) {
        log.debug(String.format(LOG_MSG_CREATING_BOOK, book.getCode().value(), group.getCode()));
        AddBookTypeRequest req = new AddBookTypeRequest();
        req.setCode(book.getCode().value());
        req.setGroupCode(group.getCode());
        service.add(req);
    }

    private void createGroup(XmlGroupType group, String parentCode) {
        log.debug(String.format(LOG_MSG_CREATING_GROUP, group.getCode(), parentCode));
        AddBookTypeGroupRequest req = new AddBookTypeGroupRequest();
        req.setCode(group.getCode());
        req.setParentCode(parentCode);
        service.add(req);
    }

    private void createGroups(List<XmlGroupType> groups, String parentCode) {
        for (XmlGroupType group : groups) {
            createGroup(group, parentCode);
            createGroups(group.getGroups(), group.getCode());
            for (XmlBookType book : group.getBooks()) {
                createBookType(book, group);
            }
        }
    }

    @Override
    public String process() throws Exception {
        Properties params = BatchRuntime.getJobOperator().getParameters(jobContext.getExecutionId());
        File f = new File(params.getProperty(PARAMETER_DOC_PATH));
        JAXBContext jc = JAXBContext.newInstance(XmlCanon.class);
        XmlCanon canon = (XmlCanon) jc.createUnmarshaller().unmarshal(f);
        createGroups(canon.getGroups(), null);
        return "SUCCESS";
    }

}
