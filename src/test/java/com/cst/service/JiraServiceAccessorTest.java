package com.cst.service;

import com.cst.CSTConfiguration;
import com.cst.model.JiraResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class JiraServiceAccessorTest {


    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Mock
    private CSTConfiguration cstConfiguration;

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse httpResponse;

    @Mock
    private HttpEntity httpEntity;


    private JiraServiceAccessor jiraServiceAccessor;

    @Before
    public void init() {
        jiraServiceAccessor = new JiraServiceAccessor(cstConfiguration, httpClient);
    }


    @Test
    public void testGetJiraResponses() throws IOException {
        Mockito.when(cstConfiguration.getJiraUrl()).thenReturn("http://localhost:4553");
        Mockito.when(httpClient.execute(Mockito.any())).thenReturn(httpResponse);
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);


        Map storyPointMap1 = Maps.newHashMap();
        Map storyPointMap2 = Maps.newHashMap();
        storyPointMap1.put("storyPoints", "1");
        storyPointMap2.put("storyPoints", "2");
        JiraResponse jiraResponse1 =
                new JiraResponse("TEST1", storyPointMap1);

        JiraResponse jiraResponse2 =
                new JiraResponse("TEST2", storyPointMap2);
        List<JiraResponse> expectedJiraResponseList = Lists.newArrayList();
        expectedJiraResponseList.add(jiraResponse1);
        expectedJiraResponseList.add(jiraResponse2);

        String expectedJiraResponseListStr = OBJECT_MAPPER.writeValueAsString(expectedJiraResponseList);

        InputStream is = new ByteArrayInputStream(expectedJiraResponseListStr.getBytes());

        Mockito.when(httpEntity.getContent()).thenReturn(is);

        List<JiraResponse> obtainedJiraResponseList =
                jiraServiceAccessor.getJiraResponses("test_query");

        assertEquals(obtainedJiraResponseList.toString(), expectedJiraResponseList.toString());

    }
}