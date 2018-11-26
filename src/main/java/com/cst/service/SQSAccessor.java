package com.cst.service;

import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.cst.CSTConfiguration;
import com.cst.model.SQSMessage;
import com.google.inject.Inject;

public class SQSAccessor {
    private final AmazonSQSClient amazonSQSClient;
    private final CSTConfiguration cstConfiguration;

    @Inject
    public SQSAccessor(AmazonSQSClient amazonSQSClient, CSTConfiguration cstConfiguration) {
        this.amazonSQSClient = amazonSQSClient;
        this.cstConfiguration = cstConfiguration;
    }

    public void sendMessage(SQSMessage sqsMessage) {
        String queueUrl = cstConfiguration.getQueueName();
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(amazonSQSClient.getQueueUrl(queueUrl).getQueueUrl())
                .withMessageBody(sqsMessage.toString());
        amazonSQSClient.sendMessage(sendMessageRequest);
    }

    public String readMessage() {
        String queueUrl = cstConfiguration.getQueueName();
        return amazonSQSClient.receiveMessage(amazonSQSClient.getQueueUrl(queueUrl).getQueueUrl()).toString();
    }
}
