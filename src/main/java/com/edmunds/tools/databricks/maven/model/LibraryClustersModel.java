package com.edmunds.tools.databricks.maven.model;

import java.util.Collection;

/**
 * Used to serialize artifact and cluster data, for library attachment.
 *
 * //TODO there is no reason to have a separate LibraryClustersModel and JobTemplatesModel.
 * // We should have file where we serialize all information pertaining to a project for None-Project invocation.
 */
public class LibraryClustersModel {
    private String artifactPath;
    private Collection<String> clusterNames;

    /**
     * Don't use this - it's for jackson deserialization only!
     */
    public LibraryClustersModel() {
    }

    public LibraryClustersModel(String artifactPath, Collection<String> clusterNames) {
        this.artifactPath = artifactPath;
        this.clusterNames = clusterNames;
    }

    public String getArtifactPath() {
        return artifactPath;
    }

    public Collection<String> getClusterNames() {
        return clusterNames;
    }
}
