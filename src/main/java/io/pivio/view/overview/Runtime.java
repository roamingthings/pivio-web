package io.pivio.view.overview;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Runtime {

    public String ram;
    public String cpu;
    public String disk;
    public String host_type;

    @Override
    public String toString() {
        return "Runtime{" +
                "ram='" + ram + '\'' +
                ", cpu='" + cpu + '\'' +
                ", disk='" + disk + '\'' +
                ", host_type='" + host_type + '\'' +
                '}';
    }
}
