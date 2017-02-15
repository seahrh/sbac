( function(IndexPage, $, undefined) {

        IndexPage.upgrade = function() {
            searchBox();
            console.log("IndexPage upgraded");
        };

        function searchBox() {
            var $input = $("#sbac-index__search__input");
            $input.typeahead({
                source : ["already", "allision", "alkanasda"],
                autoSelect : true
            });
            $input.change(function() {
                var current = $input.typeahead("getActive");
                console.log(current);
            });
        }

    }(window.sbac.IndexPage = window.sbac.IndexPage || {}, jQuery));
