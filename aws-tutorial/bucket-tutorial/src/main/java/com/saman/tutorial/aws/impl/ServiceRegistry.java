package com.saman.tutorial.aws.impl;

import com.saman.tutorial.aws.contract.BucketService;
import com.saman.tutorial.aws.contract.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static com.saman.tutorial.aws.utils.BeanUtils.loadClass;
import static com.saman.tutorial.aws.utils.BeanUtils.loadServices;
import static java.util.Collections.synchronizedMap;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@SuppressWarnings({"unused", "unchecked"})
public final class ServiceRegistry {

    public static final String SERVICES_PATH = "META-INF/services";

    private static final Map<String, Object> map = synchronizedMap(new HashMap<>());

    static {
        Stream.of(loadClass(SERVICES_PATH))
                .map(it -> loadServices(it, o -> ((Service) o).getName()))
                .forEach(map::putAll);
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
