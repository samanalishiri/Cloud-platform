package com.saman.tutorial.aws.utils;

import com.saman.tutorial.aws.impl.ServiceRegistry;
import io.vavr.control.Try;

import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
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

    public static URI toUri(String path) {
        ClassLoader classLoader = ServiceRegistry.class.getClassLoader();
        URL dir = classLoader.getResource(path);
        Objects.requireNonNull(dir);
        return Try.of(dir::toURI).get();
    }
}
