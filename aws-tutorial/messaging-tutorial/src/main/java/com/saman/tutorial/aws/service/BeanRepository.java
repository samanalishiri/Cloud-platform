package com.saman.tutorial.aws.service;

import com.saman.tutorial.aws.impl.Bean;
import com.saman.tutorial.aws.service.Bean;
import com.saman.tutorial.aws.service.Composite;

import java.util.HashMap;
import java.util.Map;

import static com.saman.tutorial.aws.utils.BeanUtils.loadClass;
import static com.saman.tutorial.aws.utils.BeanUtils.loadServices;
import static java.util.Arrays.stream;
import static java.util.Collections.synchronizedMap;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@SuppressWarnings({"unused", "unchecked"})
public final class BeanRepository {

    public static final String SERVICES_PATH = "META-INF/services";

    private static final Map<String, Object> map = synchronizedMap(new HashMap<>());

    static {
        stream(loadClass(SERVICES_PATH))
                .map(it -> loadServices(it, o -> ((Bean) o).getName()))
                .forEach(map::putAll);

        map.entrySet().stream()
                .filter(it -> it.getValue() instanceof Composite)
                .forEach(it -> ((Composite) it.getValue()).postConstruct());
    }

    private BeanRepository() {

    }

    public static void register(String name, Object bean) {
        map.put(name, bean);
    }

    public static <T> T getBean(String name) {
        return (T) map.get(name);
    }

    public static <T> T getBean(String name, Class<T> type) {
        return type.cast(map.get(name));
    }

}
