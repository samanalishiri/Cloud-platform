package com.saman.tutorial.aws;

import com.saman.tutorial.aws.contract.BucketService;
import com.saman.tutorial.aws.impl.BucketServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.DeleteBucketResponse;
import software.amazon.awssdk.services.s3.model.DeleteObjectsResponse;
import software.amazon.awssdk.services.s3.model.HeadBucketResponse;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.util.List;
import java.util.Optional;

import static com.saman.tutorial.aws.impl.ServiceRegistry.getService;
import static com.saman.tutorial.aws.utils.IoUtils.createFile;
import static com.saman.tutorial.aws.utils.IoUtils.readFile;
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

    @ParameterizedTest(name = "{index} => name=''{0}''")
    @ValueSource(strings = {"pine-framework-test-bucket"})
    @DisplayName("bucket sync creation")
    @Order(1)
    void createBucket_GivenBucketNameAs1stParamAndFalseAs2ndParam_WhenSendCreateRequest_ThenItShouldBeWaitUntilGetResponse(String name) {
        Optional<HeadBucketResponse> response = bucketService.createBucket(name, false);
        assertTrue(response.isPresent());
        response.ifPresent(it -> {
            assertTrue(it.sdkHttpResponse().isSuccessful());
            assertEquals(200, it.sdkHttpResponse().statusCode());
            assertTrue(it.sdkHttpResponse().statusText().isPresent());
            it.sdkHttpResponse().statusText().ifPresent(status -> assertEquals("OK", status));
        });
    }

    @Test
    @DisplayName("get all buckets")
    @Order(2)
    void getAllBuckets_GivenNoParam_WhenSendGetRequestToAWS_ThenReturnAllBucketAsList() {
        List<Bucket> buckets = bucketService.getAllBuckets();
        assertNotNull(buckets);
    }

    @ParameterizedTest(name = "{index} => name=''{0}''")
    @ValueSource(strings = {"pine-framework-test-bucket"})
    @DisplayName("get one bucket")
    @Order(3)
    void getOneBucket_GivenBucketNameAsParam_WhenSendGetRequestToAWS_ThenReturnTheBucket(String name) {
        Optional<Bucket> bucket = bucketService.getOneBucket(name);
        assertTrue(bucket.isPresent());
        bucket.ifPresent(it -> assertEquals(name, it.name()));
    }

    @ParameterizedTest(name = "{index} => bucketName=''{0}'', objectKey=''{1}'', fileName=''{2}''")
    @CsvSource({"pine-framework-test-bucket, aws-object, src/test/resources/put-to-aws.txt"})
    @DisplayName("put one object")
    @Order(4)
    void putOneObject_GivenBucketNameAndObjectKeyAndFileNameAsParam_WhenSendPutObjectRequestToAWS_ThenReturnTheOKStatus(
            String bucketName, String objectKey, String fileName) {

        Optional<PutObjectResponse> response = bucketService.putOneObject(bucketName, objectKey, readFile(fileName));
        assertTrue(response.isPresent());
        response.ifPresent(it -> {
            assertTrue(it.sdkHttpResponse().isSuccessful());
            assertEquals(200, it.sdkHttpResponse().statusCode());
            assertTrue(it.sdkHttpResponse().statusText().isPresent());
            it.sdkHttpResponse().statusText().ifPresent(status -> assertEquals("OK", status));
            assertNotNull(it.eTag());
        });
    }

    @ParameterizedTest(name = "{index} => bucketName=''{0}'', objectKey=''{1}'', filePath=''{2}''")
    @CsvSource({"pine-framework-test-bucket, aws-object, src/test/resources/get-from-aws"})
    @DisplayName("get one object")
    @Order(5)
    void getOneObject_GivenBucketNameAndObjectKeyAndFilePathAsParam_WhenSendGetObjectRequestToAWS_ThenReturnTheByteArray(
            String bucketName, String objectKey, String filePath) {

        byte[] object = bucketService.getOneObject(bucketName, objectKey);
        assertNotNull(object);
        createFile(String.format("%s_%d.txt", filePath, System.currentTimeMillis()), object);
    }


    @ParameterizedTest(name = "{index} => bucketName=''{0}'', objectKey=''{1}''")
    @CsvSource({"pine-framework-test-bucket, aws-object, src/test/resources/get-from-aws.txt"})
    @DisplayName("delete one object")
    @Order(5)
    void deleteOneObject_GivenBucketNameAndObjectKeyAsParam_WhenSendDeleteObjectRequestToAWS_ThenReturnTheOKStatus(
            String bucketName, String objectKey) {

        Optional<DeleteObjectsResponse> response = bucketService.deleteOneObject(bucketName, objectKey);
        assertTrue(response.isPresent());
        response.ifPresent(it -> {
            assertTrue(it.sdkHttpResponse().isSuccessful());
            assertEquals(200, it.sdkHttpResponse().statusCode());
            assertTrue(it.sdkHttpResponse().statusText().isPresent());
            it.sdkHttpResponse().statusText().ifPresent(status -> assertEquals("OK", status));
        });

    }

    @ParameterizedTest(name = "{index} => name=''{0}''")
    @ValueSource(strings = {"pine-framework-test-bucket"})
    @DisplayName("delete one bucket")
    @Order(7)
    void deleteOneBucket_GivenBucketNameAsParam_WhenSendDeleteRequestToAWS_ThenItShouldBeRemove(String name) {
        Optional<DeleteBucketResponse> response = bucketService.deleteOneBucket(name);
        assertTrue(response.isPresent());
        response.ifPresent(it -> {
            assertTrue(it.sdkHttpResponse().isSuccessful());
            assertEquals(204, it.sdkHttpResponse().statusCode());
            assertTrue(it.sdkHttpResponse().statusText().isPresent());
            it.sdkHttpResponse().statusText().ifPresent(status -> assertEquals("No Content", status));
        });
    }
}