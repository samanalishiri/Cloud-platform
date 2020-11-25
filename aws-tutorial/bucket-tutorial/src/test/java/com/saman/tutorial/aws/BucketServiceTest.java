package com.saman.tutorial.aws;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.HeadBucketResponse;

import java.util.List;
import java.util.Optional;

import static com.saman.tutorial.aws.ServiceRegistry.getService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@DisplayName("Bucket Service Tests")
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class BucketServiceTest {

    private final BucketService bucketService = getService(BucketServiceImpl.class.getSimpleName(), BucketService.class);

    @BeforeEach
    void setUp() {
        assertNotNull(bucketService);
    }

    @ParameterizedTest
    @ValueSource(strings = {"pine-framework-hello.world.test-bucket"})
    @DisplayName("bucket sync creation")
    @Order(1)
    void create_GivenBucketNameAs1stParamAndFalseAs2ndParam_WhenSendCreateRequest_ThenItShouldBeWaitUntilGetResponse(String name) {
        Optional<HeadBucketResponse> response = bucketService.create(name, false);
        assertTrue(response.isPresent());
        response.ifPresentOrElse(it -> {
                    assertTrue(it.sdkHttpResponse().isSuccessful());
                    assertEquals(200, it.sdkHttpResponse().statusCode());
                    assertTrue(it.sdkHttpResponse().statusText().isPresent());
                    it.sdkHttpResponse().statusText().ifPresent(status -> assertEquals("OK", status));
                },
                Assertions::fail);
    }

    @Test
    @DisplayName("get all bucket")
    @Order(2)
    void getAll_GivenNoParam_WhenSendRequestToAWS_ThenReturnAllBucketAsList() {
        List<Bucket> buckets = bucketService.getAll();
        assertNotNull(buckets);
    }
}