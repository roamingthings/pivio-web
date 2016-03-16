package io.pivio.view.overview.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Network {

    public String network_zone;

    @Override
    public String toString() {
        return "Network{" +
                "network_zone='" + network_zone + '\'' +
                '}';
    }
}
