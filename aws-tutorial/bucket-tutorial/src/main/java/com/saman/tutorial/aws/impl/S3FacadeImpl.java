package com.saman.tutorial.aws.impl;

import com.saman.tutorial.aws.contract.S3Facade;
import com.saman.tutorial.aws.contract.BucketObjectAsyncService;
import com.saman.tutorial.aws.contract.BucketObjectService;
import com.saman.tutorial.aws.contract.BucketService;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.DeleteBucketResponse;
import software.amazon.awssdk.services.s3.model.DeleteObjectsResponse;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.HeadBucketResponse;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import static com.saman.tutorial.aws.impl.BeanRepository.getBean;
import static java.util.Objects.requireNonNull;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public final class S3FacadeImpl implements S3Facade {

    private BucketService bucketService;

    private BucketObjectService objectService;

    private BucketObjectAsyncService objectAsyncService;

    @Override
    public void postConstruct() {
        bucketService = getBean(BucketServiceImpl.class.getSimpleName(), BucketService.class);
        objectService = getBean(BucketObjectServiceImpl.class.getSimpleName(), BucketObjectService.class);
        objectAsyncService = getBean(BucketObjectAsyncServiceImpl.class.getSimpleName(), BucketObjectAsyncService.class);
    }

    @Override
    public Optional<HeadBucketResponse> createBucket(String name) {
        requireNonNull(name);
        return bucketService.createBucket(name);
    }

    @Override
    public Optional<Bucket> getOneBucket(String name) {
        requireNonNull(name);
        return bucketService.getOneBucket(name);
    }

    @Override
    public Optional<DeleteBucketResponse> deleteOneBucket(String name) {
        requireNonNull(name);
        return bucketService.deleteOneBucket(name);
    }

    @Override
    public List<Bucket> getAllBuckets() {
        return bucketService.getAllBuckets();
    }

    @Override
    public Optional<PutObjectResponse> putOneObject(String bucketName, String objectKey, byte[] object) {
        requireNonNull(bucketName);
        requireNonNull(objectKey);
        requireNonNull(object);

        return objectService.putOneObject(bucketName, objectKey, object);
    }

    @Override
    public byte[] getOneObject(String bucketName, String objectKey) {
        requireNonNull(bucketName);
        requireNonNull(objectKey);

        return objectService.getOneObject(bucketName, objectKey);
    }

    @Override
    public Optional<DeleteObjectsResponse> deleteOneObject(String bucketName, String objectKey) {
        requireNonNull(bucketName);
        requireNonNull(objectKey);

        return objectService.deleteOneObject(bucketName, objectKey);
    }

    @Override
    public void putOneObject(String bucketName, String objectKey, byte[] object, BiConsumer<? super PutObjectResponse, ? super Throwable> action) {
        requireNonNull(bucketName);
        requireNonNull(objectKey);
        requireNonNull(object);
        objectAsyncService.putOneObject(bucketName, objectKey, object, action);
    }

    @Override
    public void getOneObject(String bucketName, String objectKey, BiConsumer<? super ResponseBytes<GetObjectResponse>, ? super Throwable> action) {
        requireNonNull(bucketName);
        requireNonNull(objectKey);
        objectAsyncService.getOneObject(bucketName, objectKey, action);
    }
}










