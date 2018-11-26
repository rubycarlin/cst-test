package com.cst.service;

import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.cst.CSTConfiguration;
import com.cst.model.SQSMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class SQSAccessorTest {

    @Mock
    private AmazonSQSClient amazonSQSClient;

    @Mock
    private CSTConfiguration cstConfiguration;

    @Mock
    private GetQueueUrlResult queueUrlResult;

    @Mock
    private SendMessageResult sendMessageResult;

    private static final String QUEUE_URL = "http://localhost:9324/queue/test";


    private SQSAccessor sqsAccessor;

    @Before
    public void init() {
        sqsAccessor = new SQSAccessor(amazonSQSClient, cstConfiguration);
    }


    @Test
    public void sendMessage() {
        Mockito.when(cstConfiguration.getQueueName()).thenReturn("test");
        Mockito.when(amazonSQSClient.getQueueUrl("test")).thenReturn(queueUrlResult);
        Mockito.when(queueUrlResult.getQueueUrl()).thenReturn(QUEUE_URL);

        SQSMessage sqsMessage = SQSMessage
                .builder()
                .name("test")
                .totalPoints(3)
                .build();

        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(QUEUE_URL)
                .withMessageBody(sqsMessage.toString());

        Mockito.doReturn(sendMessageResult).when(amazonSQSClient).sendMessage(sendMessageRequest);
    }

}