package com.saman.tutorial.aws.impl;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface Bean {
    default String getName() {
        return this.getClass().getSimpleName();
    }
}
