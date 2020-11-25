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

    private final Map<String, AwsService> map = synchronizedMap(new HashMap<>());

    private ServiceRegistry() {
        map.putAll(loadServices(AwsService.class, AwsService::getName));
    }

    public static ServiceRegistry getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public void register(String name, AwsService service) {
        map.put(name, service);
    }

    public <T extends AwsService> T get(String name) {
        return (T) map.get(name);
    }

    public <T extends AwsService> T get(String name, Class<T> type) {
        return type.cast(map.get(name));
    }

    private static class InstanceHolder {
        public static final ServiceRegistry INSTANCE = new ServiceRegistry();
    }
}
