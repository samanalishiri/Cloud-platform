package com.saman.tutorial.aws.service;

import com.saman.tutorial.aws.impl.SqsService;
import com.saman.tutorial.aws.utils.SqsUtils;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlResponse;

import java.util.Optional;

import static java.util.Optional.ofNullable;
/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public class SqsServiceImpl implements SqsService {

    public static final SqsClient SQS_CLIENT = SqsUtils.getDefaultSqlclient();

    @Override
    public Optional<GetQueueUrlResponse> createQueue(String queueName) {

        SQS_CLIENT.createQueue(CreateQueueRequest.builder()
                .queueName(queueName)
                .build());

        GetQueueUrlResponse response = SQS_CLIENT.getQueueUrl(GetQueueUrlRequest.builder()
                .queueName(queueName)
                .build());

        return ofNullable(response);
    }
}
