$(function () {

    var searchQuery = encodeURI(`{"match":{"id":"'${pivioDocumentId}'"}}`),
        queriedService = $.get(`${apiserver}/document?query=${searchQuery}`),
        network_connections = $.get(`${apiserver}/document?fields=talks_to,provides,servicename`);

    $.when(queriedService, network_connections).done((serviceData, networkData) => {
        if (serviceData[1] == "success" && networkData[1] == "success") {

            var service = serviceData[0][0]; // #WTF ?
            var servicename_map = {};

            networkData[0].map((service) => {
                servicename_map[service.servicename] = service.id;
            });

            // build details to be shown

            var detail = {
                id: service.id,
                team: service.team,
                status: service.status,
                context: service.context,
                vcs: service.vcsroot,
                description: service.description,
                links: service.links,
                name: service.name,
                network_outgoing: networkOutgoing(service, servicename_map),
                //network_incoming: networkIncoming(network_connections, service.servicename),
                provides: networkProvides(service),
                type: service.type,
                network_zone: service.network_zone,
                runtime: service.runtime,
                contact: service.contact,
                tags: service.tags,
                bounded_context: service.belongs_to_bounded_context,
                software_dependencies: getDependencies(service),
                software_licenses: getLicenses(service),
                last_upload: prettyDate(service.lastUpload),
                last_update: prettyDate(service.lastUpdate)
            };

            Handlebars.registerHelper('eachInMap', (map, block) => {
                var out = '';

                Object.keys(map).map((prop) => {
                    out += block.fn({
                        key: prop,
                        value: map[prop]
                    });
                });

                return out;
            });

            var source = $('#detail-template').html(),
                template = Handlebars.compile(source);

            $('#overview-detail').append(template(detail));
        }

    });

    function networkProvides(service) {
        var provides = [];

        if (service.provides) {
            service.provides.map((provide) => {
                const splitter = provide.split(":"),
                    name = splitter[0];
                var port = "",
                    defaultport = "";

                if (splitter.length > 1) {
                    port = splitter[1];
                }

                if (splitter.length > 2) {
                    defaultport = splitter[2];
                }

                const item = {
                    servicename: name,
                    port: port,
                    defaultport: defaultport
                };

                provides.push(item)
            });
        }

        return provides;
    }

    function getDependencies(service) {
        var
            dependencies = {},
            result = [];

        if (service.dependencies) {
            service.dependencies.map((dependency) => {
                dependencies[dependency.name] = ({
                    name: dependency.name,
                    version: dependency.version
                })
            });
        }

        var sortedKeys = Object.keys(dependencies).sort();

        for (var i = 0; i < sortedKeys.length; i++) {
            result.push(dependencies[sortedKeys[i]]);
        }
        return result;
    }

    function getLicenses(service) {
        var
            licenses = [];

        if (service.dependencies) {
            service.dependencies.map((dependency) => {
                console.log(dependency);
                if (dependency.hasOwnProperty('licenses')) {
                    for (var i = 0; i < dependency.licenses.length; i++) {
                        licenses[dependency.licenses[i].name] = dependency.licenses[i].name; // we just need the keys
                    }
                }
            });
        }

        return Object.keys(licenses).sort();
    }

    function networkOutgoing(service, servicename_map) {
        var
            outgoing = [];

        if (service.talks_to) {
            service.talks_to.map((to) => {
                const
                    servicename = to.split(":")[0],
                    path = to.split(":")[1],
                    connection = {
                        service: servicename,
                        port: path,
                        id: servicename_map[servicename]
                    };

                outgoing.push(connection);
            });
        }

        return outgoing;
    }


});
