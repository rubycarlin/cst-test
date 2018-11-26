package com.cst;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

public class CSTConfiguration extends Configuration {
    private String jiraUrl;

    private String queueUrl;

    private String queueName = "cst-test-queue";

    @JsonProperty
    public String getJiraUrl() {
        return jiraUrl;
    }

    @JsonProperty
    public void setJiraUrl(String jiraUrl) {
        this.jiraUrl = jiraUrl;
    }

    @JsonProperty
    public String getQueueUrl() {
        return queueUrl;
    }

    @JsonProperty
    public void setQueueUrl(String queueUrl) {
        this.queueUrl = queueUrl;
    }

    @JsonProperty
    public String getQueueName() {
        return queueName;
    }

    @JsonProperty
    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
}
