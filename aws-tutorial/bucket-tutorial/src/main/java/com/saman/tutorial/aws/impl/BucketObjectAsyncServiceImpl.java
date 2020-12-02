package com.saman.tutorial.aws.impl;

import com.saman.tutorial.aws.service.BucketObjectAsyncService;
import com.saman.tutorial.aws.utils.S3Utils;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import static software.amazon.awssdk.core.async.AsyncRequestBody.fromBytes;
import static software.amazon.awssdk.core.async.AsyncResponseTransformer.toBytes;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public class BucketObjectAsyncServiceImpl implements BucketObjectAsyncService {

    private final S3AsyncClient s3Client = S3Utils.getDefaultS3AsyncClient();

    @Override
    public void putOneObject(String bucketName, String objectKey, byte[] object, BiConsumer<? super PutObjectResponse, ? super Throwable> action) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();
        CompletableFuture<PutObjectResponse> future = s3Client.putObject(objectRequest, fromBytes(object));
        future.whenComplete(action);
        future.join();
    }

    @Override
    public void getOneObject(String bucketName, String objectKey, BiConsumer<? super ResponseBytes<GetObjectResponse>, ? super Throwable> action) {
        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();
        CompletableFuture<ResponseBytes<GetObjectResponse>> future = s3Client.getObject(objectRequest, toBytes());
        future.whenComplete(action);
        future.join();
    }
}
