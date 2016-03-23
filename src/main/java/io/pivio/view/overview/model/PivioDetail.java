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
    private List<UsedBy> usedBy = new ArrayList<>();

    public void setUsedBy(List<UsedBy> usedBy) {
        this.usedBy = usedBy;
        if (service != null && service.provides != null) {
            for (Service.Provides providedService : service.provides) {
                for (UsedBy by : usedBy) {
                    if (by.connectionId.equals(providedService.service_name) || by.connectionId.equals(short_name+"_"+providedService.port)) {
                        providedService.usedBy.add(by);
                    }
                }
            }
        }
    }

    public List<UsedBy> getUsedBy() {
        return usedBy;
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
