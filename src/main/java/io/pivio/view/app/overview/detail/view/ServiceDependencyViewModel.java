package io.pivio.view.app.overview.detail.view;

public class ServiceDependencyViewModel {

    public String serviceId;
    public String serviceDisplayName;

    public ServiceDependencyViewModel(String serviceId, String serviceDisplayName) {
        this.serviceId = serviceId;
        this.serviceDisplayName = serviceDisplayName;
    }
}
