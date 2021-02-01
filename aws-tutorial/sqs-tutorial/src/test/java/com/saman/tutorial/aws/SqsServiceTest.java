package com.saman.tutorial.aws;

import com.saman.tutorial.aws.impl.SqsServiceImpl;
import com.saman.tutorial.aws.service.SqsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import software.amazon.awssdk.services.sqs.model.CreateQueueResponse;
import software.amazon.awssdk.services.sqs.model.DeleteQueueResponse;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;
import software.amazon.awssdk.services.sqs.model.SendMessageBatchResponse;

import java.util.Optional;

import static com.saman.tutorial.aws.service.BeanRepository.getBean;
import static com.saman.tutorial.aws.utils.IoUtils.createFile;
import static com.saman.tutorial.aws.utils.MessageConverter.convert;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@DisplayName("SQS Service Tests")
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class SqsServiceTest {

    public static final String QUEUE_NAME = "saman-aws-tutorial-sqs";

    private final SqsService sqsService = getBean(SqsServiceImpl.class.getSimpleName(), SqsService.class);

    @BeforeEach
    void setUp() {
        assertNotNull(sqsService);
    }

    @ParameterizedTest(name = "{index} => queue name=''{0}''")
    @ValueSource(strings = {QUEUE_NAME})
    @DisplayName("sqs creation")
    @Order(1)
    void createQueue_GivenQueueNameAsParam_WhenSendCreateRequest_ThenReturnOKStatus(String name) {
        Optional<CreateQueueResponse> response = sqsService.createQueue(name);
        assertTrue(response.isPresent());
        response.ifPresent(it -> {
            assertTrue(it.sdkHttpResponse().isSuccessful());
            assertEquals(200, it.sdkHttpResponse().statusCode());
            assertTrue(it.sdkHttpResponse().statusText().isPresent());
            it.sdkHttpResponse().statusText().ifPresent(status -> assertEquals("OK", status));
            assertNotNull(it.queueUrl());
        });
    }

    @ParameterizedTest(name = "{index} => queue name=''{0}'', message=''{1}''")
    @CsvSource(value = {QUEUE_NAME + ",Hello", QUEUE_NAME + ",I am a client", QUEUE_NAME + ",Bye"})
    @DisplayName("send a message")
    @Order(2)
    void sendMessage_GivenQueueUrlAs1stAndMessageAs2dnParam_WhenSendRequest_ThenReturnOKStatus(String queueName, String message) {
        Optional<SendMessageBatchResponse> response = sqsService.sendMessages(queueName, message);
        assertTrue(response.isPresent());
        response.ifPresent(it -> {
            assertTrue(it.sdkHttpResponse().isSuccessful());
            assertEquals(200, it.sdkHttpResponse().statusCode());
            assertTrue(it.sdkHttpResponse().statusText().isPresent());
            it.sdkHttpResponse().statusText().ifPresent(status -> assertEquals("OK", status));
        });
    }

    @ParameterizedTest(name = "{index} => queue name=''{0}''")
    @ValueSource(strings = {QUEUE_NAME, QUEUE_NAME, QUEUE_NAME})
    @DisplayName("receive message")
    @Order(3)
    void receiveMessage_GivenQueueNameAsParam_WhenSendReceiveMessageRequest_ThenReturnOKStatusAndMessages(String name) {
        Optional<ReceiveMessageResponse> response = sqsService.receiveMessages(name);
        assertTrue(response.isPresent());
        response.ifPresent(it -> {
            assertTrue(it.sdkHttpResponse().isSuccessful());
            assertEquals(200, it.sdkHttpResponse().statusCode());
            assertTrue(it.sdkHttpResponse().statusText().isPresent());
            it.sdkHttpResponse().statusText().ifPresent(status -> assertEquals("OK", status));
            assertTrue(it.hasMessages());
            createFile(format("src/test/resources/%s_%d.txt", "message", System.currentTimeMillis()), convert(it.messages()));
        });
    }

    @ParameterizedTest(name = "{index} => queue name=''{0}''")
    @ValueSource(strings = {QUEUE_NAME})
    @DisplayName("sqs delete")
    @Order(4)
    void deleteQueue_GivenQueueNameAsParam_WhenSendDeleteRequest_ThenReturnOKStatus(String name) {
        Optional<DeleteQueueResponse> response = sqsService.deleteQueue(name);
        assertTrue(response.isPresent());
        response.ifPresent(it -> {
            assertTrue(it.sdkHttpResponse().isSuccessful());
            assertEquals(200, it.sdkHttpResponse().statusCode());
            assertTrue(it.sdkHttpResponse().statusText().isPresent());
            it.sdkHttpResponse().statusText().ifPresent(status -> assertEquals("OK", status));
        });
    }
}