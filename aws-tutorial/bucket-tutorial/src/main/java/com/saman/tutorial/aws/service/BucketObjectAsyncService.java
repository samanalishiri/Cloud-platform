package com.saman.tutorial.aws.service;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.util.function.BiConsumer;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface BucketObjectAsyncService extends Bean {

    void putOneObject(String bucketName, String objectKey, byte[] object, BiConsumer<? super PutObjectResponse, ? super Throwable> action);

    void getOneObject(String bucketName, String objectKey, BiConsumer<? super ResponseBytes<GetObjectResponse>, ? super Throwable> action);
}
