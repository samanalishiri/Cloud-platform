package com.saman.tutorial.aws;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.auth.credentials.AwsCredentials;

import java.util.Optional;

import static com.saman.tutorial.aws.CredentialsUtils.loadCredentials;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@DisplayName("Credentials Tests")
public class CredentialsUtilsTest {

    @Test
    @DisplayName("load default credentials")
    void loadCredentials_GivenNoParam_WhenLoadDefaultCredentials_ThenItShouldBeLoadDefaultProfile() {
        Optional<AwsCredentials> credentials = loadCredentials();
        assertTrue(credentials.isPresent());
        credentials.ifPresent(it -> {
            assertNotNull(it.accessKeyId());
            assertNotNull(it.secretAccessKey());
        });
    }
}