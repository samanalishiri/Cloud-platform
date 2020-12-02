package com.saman.tutorial.aws.impl;

import software.amazon.awssdk.services.sqs.model.GetQueueUrlResponse;

import java.util.Optional;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface SqsService extends Bean{

    Optional<GetQueueUrlResponse> createQueue(String queueName);
}
