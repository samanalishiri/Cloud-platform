package com.saman.tutorial.aws;

import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.CreateBucketConfiguration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketResponse;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

import java.util.List;
import java.util.Optional;

import static com.saman.tutorial.aws.S3Utils.DEFAULT_REGION;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public final class BucketServiceImpl implements BucketService {

    private final S3Client s3Client = S3Utils.DEFAULT_S3CLIENT;

    @Override
    public Optional<HeadBucketResponse> create(String name, Boolean async) {
        S3Waiter s3Waiter = s3Client.waiter();
        CreateBucketRequest bucketRequest = CreateBucketRequest.builder()
                .bucket(name)
                .createBucketConfiguration(CreateBucketConfiguration.builder()
                        .locationConstraint(DEFAULT_REGION.id())
                        .build())
                .build();

        s3Client.createBucket(bucketRequest);

        if (!async) {
            HeadBucketRequest bucketRequestWait = HeadBucketRequest.builder()
                    .bucket(name)
                    .build();
            WaiterResponse<HeadBucketResponse> waiterResponse = s3Waiter.waitUntilBucketExists(bucketRequestWait);
            return waiterResponse.matched().response();
        }

        return Optional.empty();
    }

    @Override
    public List<Bucket> getAll() {
        return s3Client.listBuckets(ListBucketsRequest.builder().build()).buckets();
    }
}










