import com.saman.tutorial.aws.service.S3Facade;
import com.saman.tutorial.aws.service.BucketObjectAsyncService;
import com.saman.tutorial.aws.service.BucketObjectService;
import com.saman.tutorial.aws.service.BucketService;
import com.saman.tutorial.aws.impl.S3FacadeImpl;
import com.saman.tutorial.aws.impl.BucketObjectAsyncServiceImpl;
import com.saman.tutorial.aws.impl.BucketObjectServiceImpl;
import com.saman.tutorial.aws.impl.BucketServiceImpl;

open module bucket.tutorial {
    exports com.saman.tutorial.aws.service;
    exports com.saman.tutorial.aws.impl;
    exports com.saman.tutorial.aws.utils;

    requires software.amazon.awssdk.services.s3;
    requires software.amazon.awssdk.core;
    requires software.amazon.awssdk.regions;
    requires vavr;

    uses BucketService;
    provides BucketService with BucketServiceImpl;

    uses BucketObjectService;
    provides BucketObjectService with BucketObjectServiceImpl;

    uses BucketObjectAsyncService;
    provides BucketObjectAsyncService with BucketObjectAsyncServiceImpl;

    uses S3Facade;
    provides S3Facade with S3FacadeImpl;
}
