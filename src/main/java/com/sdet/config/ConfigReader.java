package com.sdet.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Reads configuration from config.properties.
 * System properties override file properties (useful for CI/CD).
 */
public class ConfigReader {

    private static final Logger log = LoggerFactory.getLogger(ConfigReader.class);
    private static final Properties properties = new Properties();

    static {
        try (InputStream is = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (is != null) {
                properties.load(is);
                log.info("Loaded config.properties");
            } else {
                log.warn("config.properties not found on classpath");
            }
        } catch (IOException e) {
            log.error("Failed to load config.properties", e);
        }
    }

    private ConfigReader() {}

    /**
     * Returns the value for the given key.
     * System property takes priority over file property.
     */
    public static String get(String key) {
        String sysProp = System.getProperty(key);
        return (sysProp != null) ? sysProp : properties.getProperty(key);
    }

    public static String get(String key, String defaultValue) {
        String value = get(key);
        return (value != null && !value.isBlank()) ? value : defaultValue;
    }

    public static String getBaseUrl() {
        return get("base.url", "https://www.saucedemo.com");
    }
}
