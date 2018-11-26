package com.cst.resources.api.issue;

import com.cst.model.JiraResponse;
import com.cst.model.SQSMessage;
import com.cst.service.JiraServiceAccessor;
import com.cst.service.SQSAccessor;
import com.cst.transform.SQSMessageTransformer;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class IssueResourceTest {

    @Mock
    private JiraServiceAccessor jiraServiceAccessor;
    @Mock
    private SQSMessageTransformer sqsMessageTransformer;
    @Mock
    private SQSAccessor sqsAccessor;

    private IssueResource issueResource;

    @Before
    public void init() {
        issueResource = new IssueResource(jiraServiceAccessor, sqsMessageTransformer, sqsAccessor);
    }


    @Test
    public void sendSum() throws IOException {
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

        Mockito.when(jiraServiceAccessor.getJiraResponses("test_query")).thenReturn(expectedJiraResponseList);
        SQSMessage expectedSQSMessage = SQSMessage.builder()
                .name("test_name")
                .totalPoints(3)
                .build();
        Mockito.when(sqsMessageTransformer.getSQSMessage(expectedJiraResponseList, "test_name")).thenReturn(expectedSQSMessage);
        Mockito.doNothing().when(sqsAccessor).sendMessage(expectedSQSMessage);

    }
}