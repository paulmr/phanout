(function () {

    function log(msg) {
        console.log("[PMR] " + msg);
    }

    function getOphanInfoData(url) {

    }

    // if(window["jQuery"]) {
    //     console.log("Loading JQuery");
    //     browser.contentScripts.register(
    //     var script = document.createElement('script');
    //     var script.src = "./jquery.js";
    //     script.addEventListener('load', updateStats)
    //     script.addEventListener('load', addFilters)
    //     document.body.appendChild(script)
    // } else {
    //     console.log("[PMR 1642] missing jquery");
    // }

    // if(!jQuery) {
    // } else {
    //     console.log("[PMR 1634] got jquery");
    // }

    /* first, find all of the links in the page */

    var query =
        'a[href^="https://www.theguardian.com"],' +
        'a[href^="https://www.guardian.co.uk"] ,' +
        'a[href^="http://www.theguardian.com"] ,' +
        'a[href^="http://www.guardian.co.uk"]   ' ;

    function getGuardianLinks() {
        return document.querySelectorAll(query);
    }

    var links = getGuardianLinks();

    if(links.length > 0) {
        getOphanInfoData(links);
    } else {
        log("no links found");
    }

    // links.forEach(l => console.log("[PMR 1619] ", l));

})();
