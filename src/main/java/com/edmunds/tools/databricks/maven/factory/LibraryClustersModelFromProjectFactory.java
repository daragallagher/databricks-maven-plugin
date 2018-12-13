/*
 *    Copyright 2018 Edmunds.com, Inc.
 *
 *        Licensed under the Apache License, Version 2.0 (the "License");
 *        you may not use this file except in compliance with the License.
 *        You may obtain a copy of the License at
 *
 *            http://www.apache.org/licenses/LICENSE-2.0
 *
 *        Unless required by applicable law or agreed to in writing, software
 *        distributed under the License is distributed on an "AS IS" BASIS,
 *        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *        See the License for the specific language governing permissions and
 *        limitations under the License.
 */

package com.edmunds.tools.databricks.maven.factory;

import com.edmunds.tools.databricks.maven.factory.LibraryClustersModelFactory;
import com.edmunds.tools.databricks.maven.model.JobTemplateModel;
import com.edmunds.tools.databricks.maven.model.LibraryClustersModel;
import com.edmunds.tools.databricks.maven.util.ObjectMapperUtils;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import java.io.IOException;
import java.util.Arrays;

public class LibraryClustersModelFromProjectFactory extends LibraryClustersModelFactory {

    private final String deployedArtifactPath;
    private final String[] clusters;

    public LibraryClustersModelFromProjectFactory(String deployedArtifactPath,
                                                  String[] clusters) {
        this.deployedArtifactPath = deployedArtifactPath;
        this.clusters = clusters;
    }

    @Override
    public LibraryClustersModel getLibraryClustersModel() throws MojoExecutionException {
        return new LibraryClustersModel(deployedArtifactPath, Arrays.asList(clusters));
    }
}
