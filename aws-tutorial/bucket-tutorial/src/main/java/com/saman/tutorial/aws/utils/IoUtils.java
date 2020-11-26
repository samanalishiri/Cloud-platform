package com.saman.tutorial.aws.utils;

import io.vavr.control.Try;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.URL;

import static java.util.Objects.requireNonNull;

public final class IoUtils {

    private IoUtils() {
    }

    public static URI toUri(String path) {
        ClassLoader classLoader = IoUtils.class.getClassLoader();
        URL dir = classLoader.getResource(path);
        requireNonNull(dir);
        return Try.of(dir::toURI).get();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static byte[] readFile(String path) {
        File file = new File(path);

        return Try.withResources(() -> new FileInputStream(file))
                .of(inputStream -> {
                    byte[] bytes = new byte[(int) file.length()];
                    inputStream.read(bytes);
                    return bytes;
                }).get();
    }

    public static void createFile(String name, byte[] content) {
        Try.withResources(() -> new FileOutputStream(new File(name)))
                .of(outputStream -> {
                    outputStream.write(content);
                    return outputStream;
                });
    }
}
