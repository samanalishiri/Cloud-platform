module bucket.tutorial {
    exports com.saman.tutorial.aws;
    requires software.amazon.awssdk.services.s3;
    requires software.amazon.awssdk.core;
    requires software.amazon.awssdk.regions;
}