package com.saman.tutorial.aws.contract;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface Service {
    default String getName() {
        return this.getClass().getSimpleName();
    }
}
