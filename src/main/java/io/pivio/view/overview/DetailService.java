package io.pivio.view.overview;

import io.pivio.view.PivioServerConnector;
import io.pivio.view.configuration.ServerConfig;
import io.pivio.view.overview.model.PivioDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;


@Component
class DetailService {

    @Autowired
    ServerConfig serverConfig;

    @Autowired
    PivioServerConnector pivioServerConnector;

    PivioDetail getDetail(String id) {
        PivioDetail result = new PivioDetail();
        try {
            ResponseEntity pivioDetail = pivioServerConnector.query("document/",id, PivioDetail.class);
            return (PivioDetail)pivioDetail.getBody();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
