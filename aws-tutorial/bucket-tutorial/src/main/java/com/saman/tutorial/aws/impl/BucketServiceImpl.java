package com.saman.tutorial.aws.impl;

import com.saman.tutorial.aws.service.BucketService;
import com.saman.tutorial.aws.utils.S3Utils;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.CreateBucketConfiguration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteBucketResponse;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketResponse;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

import java.util.List;
import java.util.Optional;

import static com.saman.tutorial.aws.utils.S3Utils.getDefaultRegion;
import static java.util.Optional.ofNullable;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public final class BucketServiceImpl implements BucketService {

    private final S3Client s3Client = S3Utils.getDefaultS3client();

    @Override
    public Optional<HeadBucketResponse> createBucket(String name) {
        S3Waiter s3Waiter = s3Client.waiter();
        CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                .bucket(name)
                .createBucketConfiguration(CreateBucketConfiguration.builder()
                        .locationConstraint(getDefaultRegion().id())
                        .build())
                .build();

        s3Client.createBucket(createBucketRequest);

        HeadBucketRequest bucketRequestWait = HeadBucketRequest.builder()
                .bucket(name)
                .build();
        WaiterResponse<HeadBucketResponse> waiterResponse = s3Waiter.waitUntilBucketExists(bucketRequestWait);
        return waiterResponse.matched().response();
    }

    @Override
    public Optional<Bucket> getOneBucket(String name) {
        return getAllBuckets().stream()
                .filter(bucket -> bucket.name().equals(name))
                .findFirst();
    }

    @Override
    public Optional<DeleteBucketResponse> deleteOneBucket(String name) {
        return ofNullable(s3Client.deleteBucket(DeleteBucketRequest.builder().bucket(name).build()));
    }

    @Override
    public List<Bucket> getAllBuckets() {
        return s3Client.listBuckets(ListBucketsRequest.builder().build()).buckets();
    }
}










