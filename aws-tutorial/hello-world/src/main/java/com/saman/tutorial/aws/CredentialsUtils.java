package com.saman.tutorial.aws;

import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;

import java.util.Optional;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public final class CredentialsUtils {

    private CredentialsUtils() {
    }

    public static Optional<AwsCredentials> loadCredentials() {
        AwsCredentialsProvider provider = DefaultCredentialsProvider.create();
        AwsCredentials awsCredentials = provider.resolveCredentials();
        return Optional.ofNullable(awsCredentials);
    }
}
