package io.pivio.view.app.overview.detail.serverresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.lang.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Document {

    public String owner;
    public String data_format_version;
    public String description;
    public String type;
    public String vcs;
    public String name;
    public String short_name;
    public String id;
    public String contact;
    public String created;
    public String lastUpload;
    public String lastUpdate;
    public String status;
    public List<String> tags = new ArrayList<>();
    public Map<String, String> links = new HashMap<>();

    public Runtime runtime;
    public SoftwareDependency software_dependency;
    public Service service;
    public Context context;

    @Override
    public String toString() {
        return "Document{" +
                "owner='" + owner + '\'' +
                ", data_format_version='" + data_format_version + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", vcs='" + vcs + '\'' +
                ", name='" + name + '\'' +
                ", short_name='" + short_name + '\'' +
                ", id='" + id + '\'' +
                ", contact='" + contact + '\'' +
                ", created='" + created + '\'' +
                ", lastUpload='" + lastUpload + '\'' +
                ", lastUpdate='" + lastUpdate + '\'' +
                ", status='" + status + '\'' +
                ", tags=" + tags +
                ", links=" + links +
                ", runtime=" + runtime +
                ", software_dependency=" + software_dependency +
                ", service=" + service +
                ", context=" + context +
                '}';
    }
}
