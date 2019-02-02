document.addEventListener('DOMContentLoaded', function () {
    document.querySelector('#close').addEventListener('click', close);
    main();
});


function close() {
    document.getElementsByTagName("html")[0].setAttribute("style", "display:none;");
}

function main() {
    var selectedText = null;
    var url = window.location.search.substring(1); //get rid of "?" in querystring
    var qArray = url.split('&'); //get key-value pairs
    for (var i = 0; i < qArray.length; i++) {
        var pArr = qArray[i].split('='); //split key and value
        if (pArr[0] === "text")
            selectedText = pArr[1];
    }
    document.getElementById("text").textContent = selectedText;
}
