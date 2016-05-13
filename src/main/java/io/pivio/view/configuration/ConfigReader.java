package io.pivio.view.configuration;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ConfigReader {

    private final Logger log = LoggerFactory.getLogger(ConfigReader.class);

    @Value(value = "${config}")
    String configFile;

    @Autowired
    ServerConfig serverConfig;

    @PostConstruct
    void readArguments() {
        try {
            Map<String, Object> objectMap = readYamlFile(configFile);
            if (objectMap.containsKey("pages")) {
                serverConfig.pages = (List) objectMap.get("pages");
            }

            serverConfig.apiAddress = getValueFromConfigMap(objectMap, "api", serverConfig.apiAddress);
            serverConfig.jsApiAddress = getValueFromConfigMap(objectMap, "js_api", serverConfig.jsApiAddress);
            serverConfig.mainUrl = getValueFromConfigMap(objectMap, "mainUrl", serverConfig.mainUrl);

            String pivioServer = System.getenv("PIVIO_SERVER");
            if (pivioServer != null) {
                serverConfig.apiAddress = pivioServer;
            }

            String pivioServerJS = System.getenv("PIVIO_SERVER_JS");
            if (pivioServerJS != null) {
                serverConfig.jsApiAddress = pivioServerJS;
            }

            for (Object page : serverConfig.pages) {
                if (page instanceof HashMap) {
                    HashMap p = ((HashMap) page);
                    if (p.containsKey("url")) {
                        String url = (String) p.get("url");
                        if (url.startsWith("/")) {
                            p.put("url", serverConfig.mainUrl + p.get("url"));
                        }
                    }
                }
            }

            log.info("Using config: " + serverConfig.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private String getValueFromConfigMap(Map<String, Object> objectMap, String key, String defaultValue) {
        if (objectMap.containsKey(key)) {
            return (String) objectMap.get(key);
        }
        return defaultValue;
    }

    Map<String, Object> readYamlFile(String yamlFile) throws FileNotFoundException, UnsupportedEncodingException {
        File file = new File(yamlFile);
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        YamlReader yamlReader = new YamlReader(in);
        Object object = null;
        try {
            object = yamlReader.read();
        } catch (YamlException e) {
            System.out.println("Error: Yamlfile " + yamlFile);
            e.printStackTrace();
        }

        return (Map<String, Object>) object;
    }
}
