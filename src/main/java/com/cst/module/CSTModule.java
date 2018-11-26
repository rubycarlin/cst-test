package com.cst.module;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.cst.CSTConfiguration;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;

public class CSTModule implements Module {
    @Override
    public void configure(Binder binder) {
    }

    @Provides
    public AmazonSQSClient getAmazonSqsClient(CSTConfiguration cstConfiguration) {
        AmazonSQSClient amazonSQSClient = new AmazonSQSClient(new BasicAWSCredentials("x", "x"));
        String endpoint = cstConfiguration.getQueueUrl();
        amazonSQSClient.setEndpoint(endpoint);
        amazonSQSClient.createQueue(cstConfiguration.getQueueName());
        return amazonSQSClient;
    }

    @Provides
    public HttpClient getHttpClient() {
        return HttpClients.createDefault();
    }
}
