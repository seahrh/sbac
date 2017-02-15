( function(IndexPage, $, undefined) {

        IndexPage.upgrade = function() {
            searchBox();
            console.log("IndexPage upgraded");
        };

        function searchBox() {
            var $form, $input;
            $form = $("#sbac-index__search");
            if (!$form.length) {
                console.log("searchBox $form does not exist");
                return;
            }
            $input = $("#sbac-index__search__input");
            if (!$input.length) {
                console.log("searchBox $input does not exist");
                return;
            }
            $.get('names.json', function(data) {
                $input.typeahead({
                    source : data,
                    autoSelect : false
                });
            }, 'json');
            $form.submit(function(e) {
                e.preventDefault();
                submitSearch($input);
            });
        }

        function submitSearch($input) {
            var url, query;
            url = "/api/names/search/";
            if (!$input.length) {
                console.log("submitSearch $input does not exist");
                return;
            }
            query = $input.val();
            if (!query) {
                console.log("submitSearch $query must not be null or empty string");
                return;
            }
            console.log(query);
            url += query;
            $.getJSON(url).done(function(data) {
                console.log(url + ": done\nData: " + JSON.stringify(data));
                showResults(query, data);
            }).fail(function() {
                console.log(url + ": failed");
                showResults(query, []);
            }).always(function() {
                console.log(url + ": completed");
            });
        }

        function showResults(query, data) {
            var $table, $tbody, $summary, len, i;
            $summary = $("#sbac-index__search-results__summary");
            if (!$summary.length) {
                console.log("showResults $summary does not exist");
                return;
            }
            $table = $("#sbac-index__search-results__table");
            if (!$table.length) {
                console.log("showResults $table does not exist");
                return;
            }
            $tbody = $table.find("tbody");
            $tbody.empty();
            if (!data || !data.length) {
                $summary.text("No result for \"" + query + "\"");
                $tbody.append("<tr><td colspan=\"4\">no result</td></tr>");
                return;
            }
            len = data.length;
            $summary.text("Found " + len + " results for \"" + query + "\"");
            for ( i = 0; i < len; i++) {
                $tbody.append("<tr><td>" + data[i].name + "</td><td>" + data[i].gender + "</td><td>" + data[i].count + "</td><td>" + data[i].year + "</td></tr>");
            }
        }

    }(window.sbac.IndexPage = window.sbac.IndexPage || {}, jQuery));
