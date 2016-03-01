package io.pivio.view.feed;

public class FieldEntry {

    public String current;
    public String previous;
    public String field;

    public FieldEntry(String current, String previous, String field) {
        this.current = current;
        this.previous = previous;
        this.field = field;
    }
}
