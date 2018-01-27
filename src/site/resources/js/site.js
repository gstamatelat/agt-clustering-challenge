/* Open external links in new tab */

$(document).ready(function () {
    $("a[href^=http]").each(function () {
        $(this).attr({
            target: "_blank"
        });
        $(this).append(' &#8618;');
    });
});
