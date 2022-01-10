package edu.bbte.idde.jaim1826.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class ConfigFactory {
    private static Config config;
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigFactory.class);

    public static synchronized Config getConfig() {
        if (config == null) {

            try {
                InputStream inputStream = Config.class.getResourceAsStream(getConfigFileName());
                config = new ObjectMapper(new YAMLFactory()).readValue(inputStream, Config.class);
            } catch (IOException e) {
                LOGGER.error("IoException, error at getConfig", e);
                config = new Config();
                config.setDaoType("memory");
            }
            LOGGER.info("{}", config);
        }
        return config;
    }

    private static String getConfigFileName() {
        String profile = System.getenv("PROFILE");
        if (profile != null && !profile.isEmpty()) {
            LOGGER.info("Detected profile {}", profile);
            return "/application-" + profile + ".yaml";
        }
        return "/application-dev.yaml";
    }
}