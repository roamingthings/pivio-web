$(function () {

    d3.json(apiserver + "/document?query=" + encodeURI('{"match":{"type":"service"}}'), function (error, raw) {
        // from http://bl.ocks.org/mbostock/7607999

        var dependencies = [];

        raw.map(function (service) {
            var sourceName = service.short_name;
            var allTargets = [];
            if ('talks_to' in service) {
                service.talks_to.map(function (target) {
                    var targetName = target.split(":")[0];
                    var found = false;
                    allTargets.map(function (value) {
                        if (value == targetName) {
                            found = true
                        }
                    });
                    if (!found) {
                        allTargets.push(targetName)
                    }
                })
            }
            dependencies.push({name: sourceName, talks_to: allTargets})
        });

        var diameter = 760,
            radius = diameter / 2,
            innerRadius = radius - 120;

        var cluster = d3.layout.cluster()
            .size([360, innerRadius])
            .sort(null)
            .value(function (d) {
                return d.size;
            });

        var bundle = d3.layout.bundle();

        var line = d3.svg.line.radial()
            .interpolate("bundle")
            .tension(.85)
            .radius(function (d) {
                return d.y;
            })
            .angle(function (d) {
                return d.x / 180 * Math.PI;
            });

        var svg = d3.select("#graph").append("svg")
            .attr("width", diameter)
            .attr("height", diameter)
            .attr("class", "radial")
            .append("g")
            .attr("transform", "translate(" + radius + "," + radius + ")");

        var link = svg.append("g").selectAll(".link"),
            node = svg.append("g").selectAll(".node");

        var nodes = cluster.nodes(serviceDependencies(dependencies)),
            links = talks_to(nodes);

        link = link
            .data(bundle(links))
            .enter().append("path")
            .each(function (d) {
                d.source = d[0], d.target = d[d.length - 1];
            })
            .attr("class", "link")
            .attr("d", line);

        node = node
            .data(nodes.filter(function (n) {
                return !n.children;
            }))
            .enter().append("svg:a")
            .attr("xlink:href", function (d) {
                return "/app/detail/" + d.key;
            })


            .append("text")
            .attr("class", "node")
            .attr("dy", ".31em")
            .attr("transform", function (d) {
                return "rotate(" + (d.x - 90) + ")translate(" + (d.y + 8) + ",0)" + (d.x < 180 ? "" : "rotate(180)");
            })
            .style("text-anchor", function (d) {
                return d.x < 180 ? "start" : "end";
            })
            .text(function (d) {
                return d.key;
            })
            .on("mouseover", mouseovered)
            .on("mouseout", mouseouted);

        function mouseovered(d) {
            node
                .each(function (n) {
                    n.target = n.source = false;
                });

            link
                .classed("link--target", function (l) {
                    if (l.target === d) return l.source.source = true;
                })
                .classed("link--source", function (l) {
                    if (l.source === d) return l.target.target = true;
                })
                .filter(function (l) {
                    return l.target === d || l.source === d;
                })
                .each(function () {
                    this.parentNode.appendChild(this);
                });

            node
                .classed("node--target", function (n) {
                    return n.target;
                })
                .classed("node--source", function (n) {
                    return n.source;
                });
        }

        function mouseouted(d) {
            link
                .classed("link--target", false)
                .classed("link--source", false);

            node
                .classed("node--target", false)
                .classed("node--source", false);
        }

        d3.select(self.frameElement).style("height", diameter + "px");

        function serviceDependencies(dependencies) {
            var map = {};

            function find(name, data) {
                var node = map[name], i;
                if (!node) {
                    node = map[name] = data || {name: name, children: []};
                    if (name && name.length) {
                        node.parent = find(name.substring(0, i = name.lastIndexOf(".")));
                        node.parent.children.push(node);
                        node.key = name.substring(i + 1);
                    }
                }
                return node;
            }

            dependencies.forEach(function (d) {
                find(d.name, d);
            });

            return map[""];
        }

        function talks_to(nodes) {
            var map = {},
                talks_to = [];

            // Compute a map from name to node.
            nodes.forEach(function (d) {
                map[d.name] = d;
            });

            // For each import, construct a link from the source to target node.
            nodes.forEach(function (d) {
                if (d.talks_to) d.talks_to.forEach(function (i) {
                    if (map[i] != undefined) {
                        talks_to.push({source: map[d.name], target: map[i]});
                    } else {
                        console.log("Undefined Target: ", map[d.name])
                    }
                });
            });
            return talks_to;
        }
    });
});
