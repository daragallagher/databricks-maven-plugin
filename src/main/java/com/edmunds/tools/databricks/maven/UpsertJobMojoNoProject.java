/*
 *  Copyright 2018 Edmunds.com, Inc.
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 */

package com.edmunds.tools.databricks.maven;

import com.edmunds.tools.databricks.maven.factory.JobTemplateModelFactory;
import com.edmunds.tools.databricks.maven.factory.SerializedJobTemplateModelFactory;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

/**
 * Upserts databricks jobs with the given name based on the artifacts job settings json file.
 * <p>
 * This file should be in the resources directory named ${artifactId}-job-settings.json and
 * should be a serialized form of an array of type JobSettingsDTO.
 * <p>
 * NOTE: If a job does not have a unique name, it will fail unless failOnDuplicateJobName=false, in which case only the first one will be updated.
 *
 * no project is split out so we have a mojo that will work with multi-module projects
 */
@Mojo(name = "upsert-job-np", requiresProject = false)
public class UpsertJobMojoNoProject extends UpsertJobMojo {

    /**
     * The serialized job model is requierd to be passed in a NoProject scenario.
     */
    @Parameter(name = "jobTemplateModelFile", property = "jobTemplateModelFile", required = true)
    protected File jobTemplateModelFile;

    @Override
    protected JobTemplateModelFactory getJobTemplateModelFactory() {
        return new SerializedJobTemplateModelFactory(jobTemplateModelFile);
    }
}
