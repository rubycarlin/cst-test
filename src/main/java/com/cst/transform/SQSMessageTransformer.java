package com.cst.transform;

import com.cst.model.JiraResponse;
import com.cst.model.SQSMessage;

import java.util.List;

public class SQSMessageTransformer {
    public SQSMessage getSQSMessage(List<JiraResponse> jiraResponses, String name) {
        Integer totalPoints = 0;

        for (JiraResponse jiraResponse : jiraResponses) {
            totalPoints += Integer.valueOf(jiraResponse.getFields().get("storyPoints"));
        }

        return SQSMessage.builder()
                .name(name)
                .totalPoints(totalPoints)
                .build();
    }
}
