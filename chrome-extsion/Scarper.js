// ==UserScript==
// @name         User Behavior Scarper
// @namespace    http://jitr-gwu.slack.com
// @version      0.1
// @description  Scarper that can record user behavior and give recommendation with that
// @author       You
// @match        https://stackoverflow.com/*
// @grant        none
// @grant        GM_openInTab
// @grant        GM_registerMenuCommand
// @run-at       context-menu
// ==/UserScript==

(function () {
    'use strict';
    var data = {
        "url": {
            "host": "",
            "href": ""
        }
    };

    function getURL() {

        data.url.host = location.host;
        data.url.href = location.href;
    }

    window.onmouseup = function () {
        console.log("mouse up");
        if (window.getSelection()) {
            var selection = window.getSelection();
            if (selection.toString() === "") {
                return;
            }
            var selectText = null;
            selectText = selection.toString();
            alert(selectText);
        }
    };
    function test(){
        alert("Hello");
    }

    getURL();
    console.log(data)

    // Your code here...
})();
