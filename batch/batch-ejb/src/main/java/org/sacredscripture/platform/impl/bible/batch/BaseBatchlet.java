/*
 * Copyright (c) 2014, 2015 Sacred Scripture Foundation.
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

import org.sacredscripturefoundation.commons.Logger;

import javax.batch.api.AbstractBatchlet;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;

/**
 * Convenience subclass for batchlets. Provides the injected job context and an
 * instance logger.
 *
 * @author Paul Benedict
 * @since Sacred Scripture Platform 1.0
 */
abstract class BaseBatchlet extends AbstractBatchlet {

    // Log messages
    private static final String LOG_MSG_COMPLETED = "Job [{}] completed with status [{}]";
    private static final String LOG_MSG_ERRED = "Job [{}] failed with exception";
    private static final String LOG_MSG_STARTED = "Job [{}] started";

    /**
     * Instance logger.
     */
    protected final Logger log = new Logger(getClass());

    /**
     * Job context.
     */
    @Inject
    protected JobContext jobContext;

    public abstract String doProcess() throws Exception;

    @Override
    public String process() throws Exception {
        String jobName = jobContext.getJobName();
        log.info(LOG_MSG_STARTED, jobName);
        try {
            String status = doProcess();
            log.info(LOG_MSG_COMPLETED, jobName, status);
            return status;
        } catch (Throwable t) {
            log.error(LOG_MSG_ERRED, t, jobName);
            throw t;
        }
    }

}
