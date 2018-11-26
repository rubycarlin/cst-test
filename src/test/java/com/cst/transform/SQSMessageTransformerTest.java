package com.cst.transform;

import com.cst.model.JiraResponse;
import com.cst.model.SQSMessage;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class SQSMessageTransformerTest {


    private SQSMessageTransformer sqsMessageTransformer = new SQSMessageTransformer();

    @Test
    public void testGetSQSMessage() {
        Map storyPointMap1 = Maps.newHashMap();
        Map storyPointMap2 = Maps.newHashMap();
        storyPointMap1.put("storyPoints", "1");
        storyPointMap2.put("storyPoints", "2");
        JiraResponse jiraResponse1 =
                new JiraResponse("TEST1", storyPointMap1);

        JiraResponse jiraResponse2 =
                new JiraResponse("TEST2", storyPointMap2);
        List<JiraResponse> jiraResponseList = Lists.newArrayList();
        jiraResponseList.add(jiraResponse1);
        jiraResponseList.add(jiraResponse2);

        SQSMessage sqsMessage = sqsMessageTransformer.getSQSMessage(jiraResponseList, "test");
        SQSMessage expectedSQSMessage = SQSMessage.builder()
                .name("test")
                .totalPoints(3)
                .build();

        assertEquals(sqsMessage, expectedSQSMessage);

    }

}