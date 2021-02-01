package com.saman.tutorial.aws.utils;

import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.SendMessageBatchRequestEntry;

import java.util.List;
import java.util.UUID;

import static java.util.Arrays.stream;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@SuppressWarnings("unused")
public final class MessageConverter {

    private MessageConverter() {
    }

    public static SendMessageBatchRequestEntry convert(String it) {
        return SendMessageBatchRequestEntry.builder()
                .id(UUID.randomUUID().toString())
                .messageBody(it)
                .build();
    }

    public static SendMessageBatchRequestEntry[] convert(String[] strings) {
        return stream(strings).map(MessageConverter::convert).toArray(SendMessageBatchRequestEntry[]::new);
    }

    public static String[] convert(List<Message> messages) {
        return messages.stream()
                .map(Message::body)
                .toArray(String[]::new);
    }
}
