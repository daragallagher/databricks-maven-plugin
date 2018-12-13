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

import com.edmunds.tools.databricks.maven.model.JobTemplateModel;
import com.edmunds.tools.databricks.maven.model.LibraryClustersModel;
import com.edmunds.tools.databricks.maven.util.ObjectMapperUtils;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.IOException;

public class SerializedLibraryClustersModelFactory extends LibraryClustersModelFactory {

    private final File libraryClusterModelFile;

    public SerializedLibraryClustersModelFactory(File libraryClusterModelFile) {
        this.libraryClusterModelFile = libraryClusterModelFile;
    }

    @Override
    public LibraryClustersModel getLibraryClustersModel() throws MojoExecutionException {
        if (libraryClusterModelFile == null) {
            throw new MojoExecutionException("libraryClusterModelFile must be set!");
        }
        try {
            String libraryMappingModelJson = FileUtils.readFileToString(libraryClusterModelFile);
            return ObjectMapperUtils.deserialize(libraryMappingModelJson, LibraryClustersModel.class);
        } catch (IOException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }
}
