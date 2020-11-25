package com.saman.tutorial.aws;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface AwsService {
    default String getName() {
        return this.getClass().getSimpleName();
    }
}
