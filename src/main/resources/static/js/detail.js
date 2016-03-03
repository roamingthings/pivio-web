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
                vcs: service.vcs,
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
                bounded_context: service.belongs_to_bounded_context,
                software_dependencies: getDependencies(service),
                software_licenses: getLicenses(service)
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

            console.log(template(detail));

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
            dependencies = [];

        if (service.dependencies) {
            service.dependencies.map((dependency) => {
                dependencies.push({
                    name: dependency.name,
                    version: dependency.version
                })
            });
        }

        return dependencies;
    }

    function getLicenses(service) {
        var
            licenses = [];

        if (service.dependencies) {
            service.dependencies.map((dependency) => {
                if (dependency.licences) {
                    dependency.licences.map((license) => {
                        licenses[license.name] = license.name; // we just need the keys
                    })
                }
            });
        }

        return Object.keys(licenses);
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
