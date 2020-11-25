import com.saman.tutorial.aws.contract.AwsService;
import com.saman.tutorial.aws.impl.BucketServiceImpl;

module bucket.tutorial {
    exports com.saman.tutorial.aws.contract;
    exports com.saman.tutorial.aws.impl;
    exports com.saman.tutorial.aws.utils;

    requires software.amazon.awssdk.services.s3;
    requires software.amazon.awssdk.core;
    requires software.amazon.awssdk.regions;

    uses AwsService;
    provides AwsService with BucketServiceImpl;
}
