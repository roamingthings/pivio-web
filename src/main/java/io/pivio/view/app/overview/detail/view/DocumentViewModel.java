package io.pivio.view.app.overview.detail.view;

import io.pivio.view.app.overview.detail.serverresponse.Document;
import io.pivio.view.app.overview.detail.serverresponse.Provides;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentViewModel {

    public Document document;
    public List<DependentServiceViewModel> usedBy = new ArrayList<>();
    public ServiceViewModel service = new ServiceViewModel();
    public Map<String, List<DependentServiceViewModel>> dependent = new HashMap<>();

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
