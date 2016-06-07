package io.pivio.view.overview.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.pivio.view.app.overview.detail.view.DependentServiceViewModel;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Service {

    public List<Provides> provides = new ArrayList<>();
    public DependsOn depends_on = new DependsOn();

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DependsOn {

        public DependsOn() {
        }

        public List<Internal> internal = new ArrayList<>();
        public List<External> external = new ArrayList<>();

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class External {

            public External() {
            }

            public String target = "";
            public String transport_protocol = "";
            public String via = "";
            public String why = "";

            @Override
            public String toString() {
                return "External{" +
                        "target='" + target + '\'' +
                        ", transport_protocol='" + transport_protocol + '\'' +
                        ", via='" + via + '\'' +
                        ", why='" + why + '\'' +
                        '}';
            }

        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Internal {

            public Internal() {
            }

            public String service_name = "";
            public String short_name = "";
            public String port = "";
            public String why = "";
            public String label = "";

            public String getId() {
                return short_name;
            }

            @Override
            public String toString() {
                return "Internal{" +
                        "service_name='" + service_name + '\'' +
                        ", short_name='" + short_name + '\'' +
                        ", port=" + port +
                        ", why='" + why + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "DependsOn{" +
                    "internal=" + internal +
                    ", external=" + external +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Provides {

        public Provides() {
        }

        public String protocol = "";
        public String port = "";
        public String service_name = "";
        public String transport_protocol = "";
        public String description = "";
        public List<String> public_dns = new ArrayList<>();
        public List<DependentServiceViewModel> dependentServiceViewModel = new ArrayList<>();

        @Override
        public String toString() {
            return "Provides{" +
                    "protocol='" + protocol + '\'' +
                    ", port='" + port + '\'' +
                    ", service_name='" + service_name + '\'' +
                    ", transport_protocol='" + transport_protocol + '\'' +
                    ", description='" + description + '\'' +
                    ", public_dns=" + public_dns +
                    '}';
        }

        public String getTechInfo() {
            return transport_protocol + " " + protocol + " " + " " + port;
        }

    }

    @Override
    public String toString() {
        return "Service{" +
                "provides=" + provides +
                ", depends_on=" + depends_on +
                '}';
    }
}
