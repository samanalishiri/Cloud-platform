package com.saman.tutorial.aws.contract;

import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.HeadBucketResponse;

import java.util.List;
import java.util.Optional;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface BucketService extends AwsService {

    Optional<HeadBucketResponse> create(String name, Boolean async);

    Optional<Bucket> getOne(String name);

    void deleteOne(String name);

    List<Bucket> getAll();
}
