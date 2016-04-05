package io.pivio.view.overview.model;

public class Connection {

    public String id;
    public String short_name;
    public String connectionId;

    public Connection() {
    }

    public Connection(String id, String connectionId, String short_name) {
        this.id = id;
        this.connectionId = connectionId;
        this.short_name = short_name;
    }
}
