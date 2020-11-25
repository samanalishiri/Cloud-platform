package com.saman.tutorial.aws;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;

import java.util.List;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@SuppressWarnings("unused")
public final class S3Utils {

    public static final Region DEFAULT_REGION = Region.EU_CENTRAL_1;

    public static final S3Client DEFAULT_S3CLIENT = S3Client.builder()
            .region(DEFAULT_REGION)
            .build();

    private S3Utils() {
    }

    public static String[] extractBucketNames(List<Bucket> buckets) {
        return buckets.stream().map(Bucket::name).toArray(String[]::new);
    }
}
