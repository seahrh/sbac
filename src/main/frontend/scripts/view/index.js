( function(IndexPage, $, undefined) {

        IndexPage.upgrade = function() {
            searchBox();
            console.log("IndexPage upgraded");
        };

        function searchBox() {
            var $input = $("#sbac-index__search__input");
            $.get('names.json', function(data) {
                $input.typeahead({
                    source : data,
                    autoSelect : true
                });
            }, 'json');
            $input.change(function() {
                var current = $input.typeahead("getActive");
                console.log(current);
            });
        }

    }(window.sbac.IndexPage = window.sbac.IndexPage || {}, jQuery));
