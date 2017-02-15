// Json is the utility module for manipulating json
// Requires JQuery

( function(Json, $, undefined) {

        Json.pretty = function(json) {
            return JSON.stringify(json, null, "\t");
        };

        Json.fromFormData = function($form, opts) {
            var json;

            json = {};

            defaults = {
            };

            if ( typeof opts === "object") {
                opts = $.extend(defaults, opts);
            } else {
                opts = defaults;
            }

            $form.serializeArray().map(function(x) {

                var key, val;

                key = x.name;
                val = x.value;

                // Turn a multi-value property into an array

                if (json.hasOwnProperty(key)) {

                    // Create an array and insert the first value

                    if (!$.isArray(json[key])) {

                        json[key] = [json[key]];
                    }

                    json[key].push(val);

                } else {

                    json[key] = val;
                }
            });

            return json;
        };

    }(window.sbac.Json = window.sbac.Json || {}, jQuery));
