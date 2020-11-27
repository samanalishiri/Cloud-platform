import com.saman.tutorial.aws.contract.BucketFacade;
import com.saman.tutorial.aws.contract.BucketObjectAsyncService;
import com.saman.tutorial.aws.contract.BucketObjectService;
import com.saman.tutorial.aws.contract.BucketService;
import com.saman.tutorial.aws.impl.BucketFacadeImpl;
import com.saman.tutorial.aws.impl.BucketObjectAsyncServiceImpl;
import com.saman.tutorial.aws.impl.BucketObjectServiceImpl;
import com.saman.tutorial.aws.impl.BucketServiceImpl;

open module bucket.tutorial {
    exports com.saman.tutorial.aws.contract;
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

    uses BucketFacade;
    provides BucketFacade with BucketFacadeImpl;
}
