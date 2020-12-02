package com.saman.tutorial.aws.service;

import software.amazon.awssdk.services.sqs.model.CreateQueueResponse;
import software.amazon.awssdk.services.sqs.model.DeleteQueueResponse;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlResponse;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;
import software.amazon.awssdk.services.sqs.model.SendMessageBatchResponse;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface SqsService extends Bean{

    Optional<CreateQueueResponse> createQueue(String queueName);

    Optional<GetQueueUrlResponse> getQueue(String queueName);

    <T> Optional<T> getQueue(String queueName, Function<GetQueueUrlResponse, T> then);

    Optional<DeleteQueueResponse> deleteQueue(String queueName);

    Optional<SendMessageBatchResponse> sendMessages(String queueName, String... strings);

    Optional<ReceiveMessageResponse> receiveMessages(String name);
}
