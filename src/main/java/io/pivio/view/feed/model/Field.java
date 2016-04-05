package io.pivio.view.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Field {

    public Field() {
    }

    public String previous;
    public String current;
    public String field;

}
