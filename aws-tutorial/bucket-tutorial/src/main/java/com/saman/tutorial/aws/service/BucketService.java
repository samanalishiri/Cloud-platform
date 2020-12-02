package com.saman.tutorial.aws.service;

import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.DeleteBucketResponse;
import software.amazon.awssdk.services.s3.model.HeadBucketResponse;

import java.util.List;
import java.util.Optional;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface BucketService extends Bean {

    Optional<HeadBucketResponse> createBucket(String name);

    Optional<Bucket> getOneBucket(String name);

    Optional<DeleteBucketResponse> deleteOneBucket(String name);

    List<Bucket> getAllBuckets();
}
