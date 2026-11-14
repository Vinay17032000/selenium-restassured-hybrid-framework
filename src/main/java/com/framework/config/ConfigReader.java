package com.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

    private static final Properties PROPS = load();

    private ConfigReader() {
    }

    public static String get(String key) {
        String value = System.getProperty(key, PROPS.getProperty(key));
        if (value == null) {
            throw new IllegalStateException("Missing config key: " + key);
        }
        return value.trim();
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    private static Properties load() {
        Properties props = new Properties();
        try (InputStream in = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in == null) {
                throw new IllegalStateException("config.properties not found on classpath");
            }
            props.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Could not read config.properties", e);
        }
        return props;
    }
}
