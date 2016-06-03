package io.pivio.view.overview.model;

public class Connection implements Comparable {

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

    @Override
    public int compareTo(Object o) {
        return this.short_name.compareTo(((Connection)o).short_name);
    }
}
