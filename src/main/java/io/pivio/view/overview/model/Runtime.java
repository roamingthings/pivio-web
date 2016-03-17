package io.pivio.view.overview.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Runtime {

    public String ram;
    public String cpu;
    public String disk;
    public String host_type;
    public String network_zone;

    @Override
    public String toString() {
        return "Runtime{" +
                "ram='" + ram + '\'' +
                ", cpu='" + cpu + '\'' +
                ", disk='" + disk + '\'' +
                ", host_type='" + host_type + '\'' +
                ", network_zone='" + network_zone + '\'' +
                '}';
    }
}
