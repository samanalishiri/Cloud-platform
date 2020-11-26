package com.saman.tutorial.aws.impl;

import com.saman.tutorial.aws.contract.BucketObjectService;
import com.saman.tutorial.aws.utils.S3Utils;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Delete;
import software.amazon.awssdk.services.s3.model.DeleteObjectsRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectsResponse;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ObjectIdentifier;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.util.ArrayList;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static software.amazon.awssdk.core.sync.RequestBody.fromBytes;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public class BucketObjectServiceImpl implements BucketObjectService {

    private final S3Client s3Client = S3Utils.getDefaultS3client();

    @Override
    public Optional<PutObjectResponse> putOneObject(String bucketName, String objectKey, byte[] object) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();
        PutObjectResponse response = s3Client.putObject(putObjectRequest, fromBytes(object));
        return Optional.of(response);
    }

    @Override
    public byte[] getOneObject(String bucketName, String objectKey) {
        GetObjectRequest getObjectRequest = GetObjectRequest
                .builder()
                .key(objectKey)
                .bucket(bucketName)
                .build();
        ResponseBytes<GetObjectResponse> response = s3Client.getObjectAsBytes(getObjectRequest);
        return response.asByteArray();
    }

    @Override
    public Optional<DeleteObjectsResponse> deleteOneObject(String bucketName, String objectKey) {
        ArrayList<ObjectIdentifier> identifiers = new ArrayList<>();
        identifiers.add(ObjectIdentifier.builder().key(objectKey).build());

        DeleteObjectsRequest deleteObjectsRequest = DeleteObjectsRequest.builder()
                .bucket(bucketName)
                .delete(Delete.builder().objects(identifiers).build())
                .build();
        return ofNullable(s3Client.deleteObjects(deleteObjectsRequest));
    }
}
