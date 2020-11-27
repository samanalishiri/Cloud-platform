package com.saman.tutorial.aws.utils;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;

import java.util.List;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@SuppressWarnings("unused")
public final class S3Utils {

    private static final Region DEFAULT_REGION = Region.EU_CENTRAL_1;

    private static final S3Client DEFAULT_S3CLIENT = S3Client.builder()
            .region(DEFAULT_REGION)
            .build();

    private static final S3AsyncClient DEFAULT_S3_ASYNC_CLIENT = S3AsyncClient.builder()
            .region(DEFAULT_REGION)
            .build();

    private S3Utils() {
    }

    public static Region getDefaultRegion() {
        return DEFAULT_REGION;
    }

    public static S3Client getDefaultS3client() {
        return DEFAULT_S3CLIENT;
    }

    public static S3AsyncClient getDefaultS3AsyncClient() {
        return DEFAULT_S3_ASYNC_CLIENT;
    }

    public static String[] extractBucketNames(List<Bucket> buckets) {
        return buckets.stream().map(Bucket::name).toArray(String[]::new);
    }
}
