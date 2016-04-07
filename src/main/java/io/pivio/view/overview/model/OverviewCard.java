package io.pivio.view.overview.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.joda.time.DateTime;
import org.ocpsoft.prettytime.PrettyTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OverviewCard implements Comparable {

    public String name;
    public String short_name;
    public String description;
    public String lastUpload;
    public String lastUpdate;
    public Context context;
    public String id;
    public String owner;

    public String getMeta() {
        String meta = name + " " + short_name + " " + description + " " + owner + " ";
        if (context != null) {
            meta = meta + " " + context.belongs_to_bounded_context;
        }
        return meta.toLowerCase();
    }

    public String getPrettyUploadDate() {
        return new PrettyTime().format(new DateTime(lastUpload).toDate());
    }

    public String getPrettyUpdateDate() {
        return new PrettyTime().format(new DateTime(lastUpdate).toDate());
    }




    @Override
    public String toString() {
        return "Overview{" +
                "name='" + name + '\'' +
                ", short_name='" + short_name + '\'' +
                ", description='" + description + '\'' +
                ", lastUpload='" + lastUpload + '\'' +
                ", lastUpdate='" + lastUpdate + '\'' +
                ", context=" + context +
                ", id='" + id + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        return this.name.compareTo(((OverviewCard)o).name);
    }
}
