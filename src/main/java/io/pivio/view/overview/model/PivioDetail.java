package io.pivio.view.overview.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.sort;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PivioDetail {

    public String id;
    public String owner;
    public String short_name;
    public String name;
    public String status;
    public String vcs;
    public String description;
    public String type;
    public String contact;
    public Context context;
    public List<String> tags = new ArrayList<>();
    public Map<String, String> links = new HashMap<>();
    public Runtime runtime;
    public Service service;
    public String created;
    public String lastUpload;
    public String lastUpdate;
    public List<SoftwareDependency> software_dependencies = new ArrayList<>();

    public void setUsedBy(List<Connection> connection) {
        if (service != null && service.provides != null) {
            for (Service.Provides providedService : service.provides) {
                for (Connection by : connection) {
//                    if (by.connectionId.service_name.equals(providedService.service_name) || (by.connectionId.short_name.equals(short_name) &&
//                            by.connectionId.port.equals(providedService.port))) {
//                        providedService.connection.add(by);
//                    }
                }
            }
        }
    }

    public List getConsolidatedLicenses() {
        List<String> result = new ArrayList<>();
        for (SoftwareDependency software_dependency : software_dependencies) {
            for (License license : software_dependency.licenses) {
                if (!result.contains(license.name)) {
                    result.add(license.name);
                }
            }
        }
        sort(result);
        return result;
    }

    public String getShortName() {
        return short_name != null ? "(" + short_name + ")" : "";
    }

    public List<SoftwareDependency> getSortedDependencies() {
        sort(software_dependencies);
        return software_dependencies;
    }

    @Override
    public String toString() {
        return "PivioDetail{" +
                "id='" + id + '\'' +
                ", owner='" + owner + '\'' +
                ", short_name='" + short_name + '\'' +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", vcs='" + vcs + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", contact='" + contact + '\'' +
                ", context=" + context +
                ", tags=" + tags +
                ", links=" + links +
                ", runtime=" + runtime +
                '}';
    }
}
