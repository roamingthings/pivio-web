$(function () {

    var searchQuery = encodeURI(`{"match":{"id":"'${pivioDocumentId}'"}}`),
        queriedService = $.get(`${apiserver}/document?query=${searchQuery}`),
        network_connections = $.get(`${apiserver}/document?fields=service.talks_to,service.provides,short_name,id`);

    $.when(queriedService, network_connections).done(function (serviceData, networkData)  {
        if (serviceData[1] == "success" && networkData[1] == "success") {

            var pivioDocument = serviceData[0][0]; // #WTF ?
            var servicename_map = {};

            console.log(pivioDocument);

            networkData[0].map(function (document) {
                if (document.service) {
                    document.service.provides.map(function (resource){
                       console.log(resource);
                        servicename_map[resource.service_name] = document.id;
                    });
                }
            });

            // build details to be shown

            var detail = {
                id: pivioDocument.id,
                team: pivioDocument.team,
                status: pivioDocument.status,
                context: pivioDocument.context,
                vcs: pivioDocument.vcsroot,
                description: pivioDocument.description,
                links: pivioDocument.links,
                name: pivioDocument.name,
                network_outgoing: networkOutgoing(pivioDocument.service, servicename_map),
                //network_incoming: networkIncoming(network_connections, service.servicename),
                provides: networkProvides(pivioDocument.service),
                type: pivioDocument.type,
                network_zone: pivioDocument.network_zone,
                runtime: pivioDocument.runtime,
                contact: pivioDocument.contact,
                tags: pivioDocument.tags,
                short_name: pivioDocument.short_name,
                bounded_context: pivioDocument.belongs_to_bounded_context,
                software_dependencies: getDependencies(pivioDocument),
                software_licenses: getLicenses(pivioDocument),
                last_upload: prettyDate(pivioDocument.lastUpload),
                last_update: prettyDate(pivioDocument.lastUpdate)
            };

            Handlebars.registerHelper('eachInMap', function (map, block)  {
                var out = '';

                Object.keys(map).map(function (prop) {
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
            service.provides.map(function (provide)  {
                const item = {
                    servicename: provide.service_name,
                    port: provide.port
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

        if (service.software_dependencies) {
            service.software_dependencies.map(function (dependency)  {
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

        if (service.software_dependencies) {
            service.software_dependencies.map(function (dependency)  {
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
            service.talks_to.map(function (to)  {
                const
                    connection = {
                        service: to,
                        id: servicename_map[to]
                    };

                outgoing.push(connection);
            });
        }

        return outgoing;
    }


});
