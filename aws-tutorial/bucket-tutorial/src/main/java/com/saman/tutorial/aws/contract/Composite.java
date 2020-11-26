package com.saman.tutorial.aws.contract;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface Composite {

    void initDependencies();

    default boolean isComposite() {
        return true;
    }
}
