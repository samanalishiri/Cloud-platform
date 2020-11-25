package com.saman.tutorial.aws.impl;

import com.saman.tutorial.aws.contract.BucketService;
import com.saman.tutorial.aws.contract.Service;
import io.vavr.control.Try;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static com.saman.tutorial.aws.utils.BeanUtils.loadServices;
import static com.saman.tutorial.aws.utils.BeanUtils.toUri;
import static io.vavr.control.Try.run;
import static java.nio.file.Files.list;
import static java.util.Collections.synchronizedMap;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@SuppressWarnings({"unused", "unchecked"})
public final class ServiceRegistry {

    private static final Map<String, Object> map = synchronizedMap(new HashMap<>());

    static {
        run(() -> list(Paths.get(toUri("META-INF/services")))
                .map(it -> Try.of(() -> Class.forName(it.getFileName().toString())).get())
                .forEach(it -> map.putAll(loadServices(it, o -> ((Service) o).getName()))));
    }

    private ServiceRegistry() {

    }

    public static void register(String name, BucketService service) {
        map.put(name, service);
    }

    public static <T extends BucketService> T getService(String name) {
        return (T) map.get(name);
    }

    public static <T extends BucketService> T getService(String name, Class<T> type) {
        return type.cast(map.get(name));
    }
}
