package io.pivio.view;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServerConfig {
    public List pages = new ArrayList(); // show nothing for default
    public String apiAddress = "http://localhost:9123"; // default
}
