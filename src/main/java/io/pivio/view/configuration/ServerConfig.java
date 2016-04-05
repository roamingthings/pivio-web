package io.pivio.view.configuration;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServerConfig {
    public List pages = new ArrayList(); // show nothing for default
    public String apiAddress = "http://localhost:9123"; // default
    public String mainUrl = "http://localhost:8080"; // needs to be determined later to match the host with the 'main' app.

    @Override
    public String toString() {
        return "ServerConfig{" +
                "pages=" + pages +
                ", apiAddress='" + apiAddress + '\'' +
                ", mainUrl='" + mainUrl + '\'' +
                '}';
    }
}
