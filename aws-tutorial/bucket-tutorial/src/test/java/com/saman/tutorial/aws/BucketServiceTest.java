package aws;

import com.saman.tutorial.aws.BucketService;
import com.saman.tutorial.aws.ServiceRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.services.s3.model.Bucket;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@DisplayName("Bucket Service Tests")
class BucketServiceTest {

    private final BucketService bucketService = ServiceRegistry.getInstance()
            .get(BucketService.class.getSimpleName(), BucketService.class);

    @BeforeEach
    void setUp() {
        assertNotNull(bucketService);
    }

    @DisplayName("Get All Bucket")
    @Test
    void getAllBuckets_GivenNoParam_WhenSendRequestToAWS_ThenReturnAllBucketAsList() {
        List<Bucket> buckets = bucketService.GetAllBuckets();
        assertNotNull(buckets);
        assertTrue(buckets.stream().map(Bucket::name).collect(toList()).contains("pine-helloworld"));
    }

    @Test
    void loadCredentials() {
        AwsCredentialsProvider provider = DefaultCredentialsProvider.create();
        AwsCredentials awsCredentials = provider.resolveCredentials();
    }
}