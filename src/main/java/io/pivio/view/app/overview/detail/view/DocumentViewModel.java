package io.pivio.view.app.overview.detail.view;

import io.pivio.view.app.overview.detail.serverresponse.Document;
import io.pivio.view.app.overview.detail.serverresponse.Provides;

public class DocumentViewModel {

    public Document document;
    public ServiceViewModel service = new ServiceViewModel();
    public SoftwareDependencyModel softwareDependency = new SoftwareDependencyModel();

    public DocumentViewModel(Document document) {
        this.document = document;
        setupServiceViewModel();
    }

    public String getShortName() {
        return document.short_name != null ? "(" + document.short_name + ")" : "";
    }

    public void setupServiceViewModel() {
        for (Provides provide : document.service.provides) {
            service.provides.add(new ProvidesModel(provide));
        }


    }

}
