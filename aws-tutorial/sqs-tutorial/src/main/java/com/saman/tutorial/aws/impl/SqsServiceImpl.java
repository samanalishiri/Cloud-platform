package com.saman.tutorial.aws.impl;

import com.saman.tutorial.aws.service.SqsService;
import com.saman.tutorial.aws.utils.SqsUtils;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.CreateQueueResponse;
import software.amazon.awssdk.services.sqs.model.DeleteQueueRequest;
import software.amazon.awssdk.services.sqs.model.DeleteQueueResponse;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlResponse;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;
import software.amazon.awssdk.services.sqs.model.SendMessageBatchRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageBatchResponse;

import java.util.Optional;
import java.util.function.Function;

import static com.saman.tutorial.aws.utils.MessageConverter.convert;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public class SqsServiceImpl implements SqsService {

    public static final SqsClient SQS_CLIENT = SqsUtils.getDefaultSqlClient();

    @Override
    public Optional<CreateQueueResponse> createQueue(String queueName) {
        return ofNullable(SQS_CLIENT.createQueue(CreateQueueRequest.builder()
                .queueName(queueName)
                .build()));
    }

    @Override
    public Optional<GetQueueUrlResponse> getQueue(String queueName) {
        return ofNullable(SQS_CLIENT.getQueueUrl(GetQueueUrlRequest.builder()
                .queueName(queueName)
                .build()));
    }

    @Override
    public <T> Optional<T> getQueue(String queueName, Function<GetQueueUrlResponse, T> then) {
        GetQueueUrlResponse queue = SQS_CLIENT.getQueueUrl(GetQueueUrlRequest.builder()
                .queueName(queueName)
                .build());

        if (isNull(queue))
            return Optional.empty();

        return ofNullable(then.apply(queue));
    }

    @Override
    public Optional<DeleteQueueResponse> deleteQueue(String queueName) {
        return getQueue(queueName,
                (queue) -> SQS_CLIENT.deleteQueue(DeleteQueueRequest.builder()
                        .queueUrl(queue.queueUrl())
                        .build()));
    }

    @Override
    public Optional<SendMessageBatchResponse> sendMessages(String queueName, String... strings) {
        return getQueue(queueName,
                queue -> SQS_CLIENT.sendMessageBatch(SendMessageBatchRequest.builder()
                        .queueUrl(queue.queueUrl())
                        .entries(convert(strings))
                        .build()));
    }

    @Override
    public Optional<ReceiveMessageResponse> receiveMessages(String queueName) {
       return getQueue(queueName,queue -> {
           ReceiveMessageRequest receiveRequest = ReceiveMessageRequest.builder()
                   .queueUrl(queue.queueUrl())
                   .build();
           return SQS_CLIENT.receiveMessage(receiveRequest);
       });
    }

}
