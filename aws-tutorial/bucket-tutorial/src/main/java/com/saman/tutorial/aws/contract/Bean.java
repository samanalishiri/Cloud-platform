package com.saman.tutorial.aws.contract;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface Bean {
    default String getName() {
        return this.getClass().getSimpleName();
    }
}
