package com.saman.tutorial.aws;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;

import java.util.List;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public final class BucketService implements AwsService {

    private final S3Client s3Client = S3Utils.FRANKFURT_S3;

    public List<Bucket> GetAllBuckets() {
        return s3Client.listBuckets(ListBucketsRequest.builder().build()).buckets();
    }
}