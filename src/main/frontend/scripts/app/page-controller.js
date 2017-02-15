$(document).ready(function() {
    var pageIdentifier;
    pageIdentifier = $("body").prop("id");
    if (pageIdentifier === "sbac-index") {
        sbac.IndexPage.upgrade();
        return;
    }
    
});
