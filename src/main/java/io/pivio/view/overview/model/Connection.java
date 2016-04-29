package io.pivio.view.overview.model;

public class Connection {

    public String id;
    public String short_name;
    public Service.DependsOn.Internal connectionId;

    public Connection() {
    }

    public Connection(String id, Service.DependsOn.Internal connectionId, String short_name) {
        this.id = id;
        this.connectionId = connectionId;
        this.short_name = short_name;
    }
}
