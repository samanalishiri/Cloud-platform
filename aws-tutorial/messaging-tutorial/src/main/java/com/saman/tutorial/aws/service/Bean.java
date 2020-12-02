package com.saman.tutorial.aws.service;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface Bean {
    default String getName() {
        return this.getClass().getSimpleName();
    }
}
