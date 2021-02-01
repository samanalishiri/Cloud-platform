package com.saman.tutorial.aws.utils;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageBatchRequestEntry;

import java.util.UUID;

import static java.util.Arrays.stream;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@SuppressWarnings("unused")
public final class SqsUtils {

    private static final Region DEFAULT_REGION = Region.EU_CENTRAL_1;


    private static final SqsClient DEFAULT_SQS_CLIENT = SqsClient.builder()
            .region(DEFAULT_REGION)
            .build();

    private SqsUtils() {
    }

    public static Region getDefaultRegion() {
        return DEFAULT_REGION;
    }

    public static SqsClient getDefaultSqlClient() {
        return DEFAULT_SQS_CLIENT;
    }

    public static SendMessageBatchRequestEntry createMessage(String it) {
        return SendMessageBatchRequestEntry.builder()
                .id(UUID.randomUUID().toString())
                .messageBody(it)
                .build();
    }

    public static SendMessageBatchRequestEntry[] createMessage(String[] strings) {
        return stream(strings).map(SqsUtils::createMessage).toArray(SendMessageBatchRequestEntry[]::new);
    }
}
