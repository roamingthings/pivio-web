package io.pivio.view.feed;

import java.util.List;

public class FeedEntry {

    public String id;
    public String order;
    public String timestamp;
    public List<FieldEntry> fields;

    public FeedEntry(String id, String order, String timestamp, List<FieldEntry> fields) {
        this.id = id;
        this.order = order;
        this.timestamp = timestamp;
        this.fields = fields;
    }
}
