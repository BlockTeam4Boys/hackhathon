function create() {
    sendHttpRequest("GET", "welcome");

}

e1 = document.getElementById("but1");
e3 = document.getElementById("but3");
e2 = document.getElementById("but2");
e4 = document.getElementById("whom");
e5 = document.getElementById("many");
e1.style.visibility = "hidden";
e3.style.visibility = "hidden";
e2.style.visibility = "hidden";
e4.style.visibility = "hidden";
e5.style.visibility = "hidden";


a;

function sendHttpRequest(action, url) {
    var xhr = new XMLHttpRequest();

    xhr.open(action, url, false);

    xhr.send();
    if (xhr.status !== 200) {
        alert( xhr.status + ': ' + xhr.statusText ); // пример вывода: 404: Not Found
    } else {
        ans = xhr.responseText.split(';');
        a = ans
        console.log(ans[0])
        console.log(ans[1])
        console.log(ans[2])
        send("GET", "wallet?c=" + ans[1]);

    }
}

function getInfo() {
    var xhr = new XMLHttpRequest();

    xhr.open("GET", "wallet?c=" + a[1],  false);

    xhr.send();
    if (xhr.status !== 200) {
        alert( xhr.status + ': ' + xhr.statusText ); // пример вывода: 404: Not Found
    } else {
        ans = xhr.responseText.split(';');
        a = ans
        document.getElementById("balance").innerHTML= "<p> balance" + ans[2] + "</p>";
    }
}
function miner() {

    var xhr = new XMLHttpRequest();

    xhr.open("GET", "miner?c=" + a[1], false);

    xhr.send();
    if (xhr.status !== 200) {
        alert( xhr.status + ': ' + xhr.statusText ); // пример вывода: 404: Not Found
    } else {
        alert("success!")
    }
}

function sendTSR() {

    var xhr = new XMLHttpRequest();

    w = document.getElementById('whom').value;
    m = document.getElementById('many').value;
    console.log(w)
    console.log(m)

    xhr.open("GET", "send?from=" + a[1] + "&whom=" + w + "&many=" + m, false);

    xhr.send();
    if (xhr.status !== 200) {
        alert( xhr.status + ': ' + xhr.statusText ); // пример вывода: 404: Not Found
    } else {
        alert("success send!")
    }
}

function send(action, url) {
    var elem = document.getElementById("but");
    elem.parentNode.removeChild(elem);
    var elem = document.getElementById("st");
    elem.parentNode.removeChild(elem);

    var xhr = new XMLHttpRequest();

    xhr.open(action, url, false);

    xhr.send();
    if (xhr.status !== 200) {
        alert( xhr.status + ': ' + xhr.statusText ); // пример вывода: 404: Not Found
    } else {
        ans = xhr.responseText.split(';');
        document.getElementById("privat").innerHTML= "<p>" + "private key :" + ans[0] + "</p>";
        document.getElementById("public").innerHTML= "<p>" + "public key :" + ans[1] + "</p>";
        document.getElementById("balance").innerHTML= "<p> balance" + ans[2] + "</p>";

        e1 = document.getElementById("but1");
        e3 = document.getElementById("but3");
        e2 = document.getElementById("but2");
        e1.style.visibility = "visible";
        e3.style.visibility = "visible";
        e2.style.visibility = "visible";
        e4.style.visibility = "visible";
        e5.style.visibility = "visible";

    }

}
