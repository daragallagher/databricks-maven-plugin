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

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class PrepareDbResourcesTest extends DatabricksMavenPluginTestHarness {

    private final String GOAL = "prepare-db-resources";


    @BeforeClass
    public void initClass() throws Exception {
        super.setUp();
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        super.beforeMethod();
    }

    @Test
    public void testExecuteJobTemplateFile_default() throws Exception {
        PrepareDbResources underTest = getNoOverridesMojo(GOAL);
        // MUST be done otherwise invocations will read from an existing file instead.
        underTest.jobTemplateModelFile.delete();
        underTest.execute();

        String key = "unit-test-group/unit-test-artifact/1.0.0-SNAPSHOT/unit-test-artifact-1.0.0-SNAPSHOT" +
            ".jar";

        String lines = FileUtils.readFileToString(underTest.jobTemplateModelFile);
        assertThat(lines, containsString("  \"groupId\" : \"unit-test-group\","));
        assertThat(lines, containsString("  \"artifactId\" : \"unit-test-artifact\","));
        assertThat(lines, containsString("  \"version\" : \"1.0.0-SNAPSHOT\","));
        assertThat(lines, containsString("  \"environment\" : null,"));
        assertThat(lines, containsString("  \"groupWithoutCompany\" : \"unit-test-group\""));
        assertThat(lines, containsString("  \"databricks.repo\" : \"my-bucket/artifacts\""));
        assertThat(lines, containsString("  \"databricksRepo\" : \"my-bucket/artifacts\""));
        assertThat(lines, containsString("  \"databricks.repo.key\" : \"" + key +"\""));
        assertThat(lines, containsString("  \"databricksRepoKey\" : \""+key+"\""));
    }

    @Test(expectedExceptions = MojoExecutionException.class, expectedExceptionsMessageRegExp = ".*databricksRepo.*")
    public void testExecuteJobTemplateFile_missingThrowsException() throws Exception {
        PrepareDbResources underTest = getMissingMandatoryMojo(GOAL);
        // MUST be done otherwise invocations will read from an existing file instead.
        underTest.jobTemplateModelFile.delete();
        underTest.execute();
    }
/*
    //Currently I can't get this test to work for the life of me on jenkins... It fails during the copy dir stage.
    @Test(enabled = false)
    public void testNotebookCopy() throws Exception {
        String localPath = classLoader.getResource("notebooks/").getPath();
        File outputPath = new File(outputBuildDir + "notebooks").getAbsoluteFile();
        underTest.setSourceWorkspacePath(new File(localPath));
        underTest.setPackagedWorkspacePath(outputPath);
        underTest.setWorkspacePrefix("/test/mycoolartifact");
        underTest.validate = true;
        underTest.prepareNotebooks();
        underTest.validate = false;

        List<String> expectedFiles = Lists.newArrayList(
                "target/test-target/notebooks/test/mycoolartifact/test1/myFile.scala",
                "target/test-target/notebooks/test/mycoolartifact/test2/myFile.scala",
                "target/test-target/notebooks/test/mycoolartifact/test2/test3/myFile.scala");
        assertTrue(underTest.packagedWorkspacePath.exists());
        for (String expectedFile : expectedFiles) {
            assertThat(expectedFile, new File(expectedFile).exists());
        }
    }*/
}