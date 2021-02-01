package com.saman.tutorial.aws.utils;

import io.vavr.control.Try;

import java.nio.file.Paths;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.function.Function;

import static com.saman.tutorial.aws.utils.IoUtils.toUri;
import static java.nio.file.Files.list;
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

    public static Class<?>[] loadClass(String path) {
        return Try.of(() -> list(Paths.get(toUri(path)))
                .map(it -> Try.of(() -> Class.forName(it.getFileName().toString())).get())
                .toArray(Class[]::new))
                .get();
    }
}
