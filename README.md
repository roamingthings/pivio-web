# A view for the db

This is a first shot on how to view things in the DB.

# ALPHA - ALPHA - ALPHA

```
gradle build
```

# Config File


```
api: http://localhost:9123/ // Points to your Pivio DB instance
pages:
  - description: Overview
    url: /app/overview
    id: tabOverview
  - description: Query
    url: app/query
    id: tabQuery
  - description: Service Graph
    url: /app/servicegraph
    id: tabServiceGraph
  - description: Feed
    url: /app/feed
    id: tabFeed

# Run

```
java -jar build/libs/view-0.0.1-SNAPSHOT.jar -f service_config.yaml
```

