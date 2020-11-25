package com.saman.tutorial.aws.utils;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public final class BeanUtils {
    private BeanUtils() {
    }

    public static <T> Map<String, T> loadServices(Class<T> interfaceClass, Function<T, String> mapper) {
        return ServiceLoader.load(interfaceClass)
                .stream()
                .map(ServiceLoader.Provider::get)
                .collect(toMap(mapper, s -> s));
    }
}
