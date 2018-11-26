package com.cst.service;

import com.cst.CSTConfiguration;
import com.cst.model.JiraResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.util.List;

public class JiraServiceAccessor {
    private static final String JIRA_URL_PATH = "/rest/api/2/search?q=";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final CSTConfiguration cstConfiguration;
    private final HttpClient httpClient;

    @Inject
    public JiraServiceAccessor(CSTConfiguration cstConfiguration, HttpClient httpClient) {
        this.cstConfiguration = cstConfiguration;
        this.httpClient = httpClient;
    }

    public List<JiraResponse> getJiraResponses(String query) throws IOException {
        String jiraUrl = cstConfiguration.getJiraUrl();
        HttpGet request = new HttpGet(jiraUrl + JIRA_URL_PATH + query);
        HttpResponse response = httpClient.execute(request);

        return OBJECT_MAPPER.readValue(response.getEntity().getContent(), new TypeReference<List<JiraResponse>>() {
        });
    }
}
