package io.pivio.view.overview.model;

public class UsedBy {

    public String id;
    public String short_name;
    public String name;
    public String connectionId;

    public UsedBy() {
    }

    public UsedBy(String id, String connectionId) {
        this.id = id;
        this.connectionId = connectionId;
    }
}
