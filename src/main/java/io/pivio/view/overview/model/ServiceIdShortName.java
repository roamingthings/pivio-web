package io.pivio.view.overview.model;

public class ServiceIdShortName {

    public Service service;
    public String id;
    public String short_name;

    public boolean hasDependencies() {
        return service != null && service.depends_on != null;
    }

    @Override
    public String toString() {
        return "ServiceIdShortName{" +
                "service=" + service +
                ", id='" + id + '\'' +
                ", short_name='" + short_name + '\'' +
                '}';
    }
}
