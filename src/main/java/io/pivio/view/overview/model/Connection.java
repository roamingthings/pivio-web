package io.pivio.view.overview.model;

public class Connection {

    public String id;
    public Service service;
    public String short_name;

    public boolean hasDependencies() {
        return service != null && service.depends_on != null;
    }

}
