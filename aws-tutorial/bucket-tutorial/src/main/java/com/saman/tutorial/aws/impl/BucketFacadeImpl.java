package com.saman.tutorial.aws.impl;

import com.saman.tutorial.aws.contract.BucketFacade;
import com.saman.tutorial.aws.contract.BucketObjectService;
import com.saman.tutorial.aws.contract.BucketService;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.DeleteBucketResponse;
import software.amazon.awssdk.services.s3.model.DeleteObjectsResponse;
import software.amazon.awssdk.services.s3.model.HeadBucketResponse;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.util.List;
import java.util.Optional;

import static com.saman.tutorial.aws.impl.BeanRepository.getBean;
import static java.util.Objects.requireNonNull;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public final class BucketFacadeImpl implements BucketFacade {

    private BucketService bucketService;

    private BucketObjectService objectService;

    @Override
    public void initDependencies() {
        bucketService = getBean(BucketServiceImpl.class.getSimpleName(), BucketService.class);
        objectService = getBean(BucketObjectServiceImpl.class.getSimpleName(), BucketObjectService.class);
    }

    @Override
    public Optional<HeadBucketResponse> createBucket(String name, boolean async) {
        requireNonNull(name);
        return bucketService.createBucket(name, async);
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
}










