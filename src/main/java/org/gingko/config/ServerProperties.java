package org.gingko.config;

import org.gingko.util.PathUtils;
import org.gingko.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * @author Kyia, TangYing
 */
@Component
public class ServerProperties {

    private static final Logger LOG = LoggerFactory.getLogger(ServerProperties.class);

    private static final String SERVER_PROPS = "server.properties";
    private static Properties props = new Properties();

    static {
        String filePath = PathUtils.getConfPath() + File.separator + SERVER_PROPS;

        try {
            InputStreamReader in = new InputStreamReader(new FileInputStream(filePath), Charset.forName("UTF-8"));
            props.load(in);

            // SEC
            langLocale = get("lang.locale");

            LOG.info("Remote server properties load completed.");
        } catch (IOException e) {
            LOG.error("Remote server properties load failed.", e);
        }
    }

    public static String langLocale = "zh_cn";

    /**
     * Get from properties file with blank filter
     *
     * @param key
     * @return
     */
    public static String get(String key) {
        return StringUtils.replaceBlank(props.getProperty(key));
    }
}
