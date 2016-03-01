package io.pivio.view.configuration;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Component
public class ConfigReader {


    @Value("${config}")
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
            if (objectMap.containsKey("api")) {
                serverConfig.apiAddress = (String) objectMap.get("api");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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
