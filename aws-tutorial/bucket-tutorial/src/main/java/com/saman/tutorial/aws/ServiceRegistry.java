package com.saman.tutorial.aws;

import java.util.HashMap;
import java.util.Map;

import static com.saman.tutorial.aws.BeanUtils.loadServices;
import static java.util.Collections.synchronizedMap;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@SuppressWarnings({"unused", "unchecked"})
public final class ServiceRegistry {

    private static final Map<String, AwsService> map = synchronizedMap(new HashMap<>());

    static {
        map.putAll(loadServices(AwsService.class, AwsService::getName));
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
