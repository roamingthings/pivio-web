package io.pivio.view.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.joda.time.DateTime;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedItem {

    public String document;
    public int order;
    public Date timestamp;
    public List<Field> fields = new ArrayList<>();

    public FeedItem() {
    }

    public String getPrettyDate() {
        return new PrettyTime().format(new DateTime(timestamp).toDate());
    }
}
