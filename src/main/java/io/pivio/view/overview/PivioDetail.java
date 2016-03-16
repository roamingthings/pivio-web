package io.pivio.view.overview;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Network network;
    public Service service;
    public String created;
    public String lastUpload;
    public String lastUpdate;

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
                ", network=" + network +
                '}';
    }
}
