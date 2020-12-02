package com.saman.tutorial.aws.utils;

import io.vavr.control.Try;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import static java.util.Objects.requireNonNull;
/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public final class IoUtils {

    private IoUtils() {
    }

    public static URI toUri(String path) {
        URL directory = IoUtils.class.getClassLoader().getResource(path);
        requireNonNull(directory);
        return Try.of(directory::toURI).get();
    }

    public static byte[] readFile(String path) {
        return Try.withResources(() -> new FileInputStream(new File(path)))
                .of(InputStream::readAllBytes)
                .get();
    }

    public static void createFile(String name, byte[] content) {
        Try.withResources(() -> new FileOutputStream(new File(name)))
                .of(outputStream -> {
                    outputStream.write(content);
                    return outputStream;
                });
    }
}
