package com.northface.automation.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads and exposes values from {@code config.properties}.
 * <p>
 * The file is read once (static block) so every caller shares a single
 * {@link Properties} instance. System properties override file values,
 * which lets you tweak a run from the command line, e.g.:
 * <pre>mvn test -Dheadless=true -Dbrowser=chrome</pre>
 */
public final class ConfigReader {

    private static final Properties PROPERTIES = new Properties();

    // Private constructor: this is a static utility class.
    private ConfigReader() {
    }

    static {
        try (InputStream input =
                     ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found on the classpath");
            }
            PROPERTIES.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    /**
     * @param key property key
     * @return the value, with any matching system property (-Dkey=value) taking precedence
     */
    public static String get(String key) {
        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue.trim();
        }
        String value = PROPERTIES.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Missing property '" + key + "' in config.properties");
        }
        return value.trim();
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }
}
