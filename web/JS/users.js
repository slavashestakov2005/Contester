function getCookie(document, name) {
    var r = document.cookie.match("(^|;) ?" + name + "=([^;]*)(;|$)");
    if (r) return r[2].trim();
    else return "";
}

function query(parameters){
    var result = "?";
    parameters.forEach((value, key) => {
        result += encodeURIComponent(key) + '=' + encodeURIComponent(value) + "&";
    });
    return result;
}

function ToCorrectText(s){
    var el = document.createElement("div");
    el.innerText = el.textContent = s;
    s = el.innerHTML;
    return s;
}


function textInput(document) {
    var textarea = document.getElementById("code_text");
    textarea.style.height = '1px';
    textarea.style.height = (textarea.scrollHeight + 6) + 'px';
    if (textarea.value != null && textarea.value !== "") codeInput(document, 'text');
}

function codeInput(document, type) {
    var codeFile = document.getElementById("code_file");
    var codeText = document.getElementById("code_text");
    if (type === "file") codeText.value = "";
    if (type === "text") codeFile.value = "";
}

function readFile(document) {
    var codeFile = document.getElementById("code_file");
    var codeText = document.getElementById("code_text");
    let file = codeFile.files[0];
    let reader = new FileReader();
    reader.readAsText(file);
    reader.onload = function(){
        codeText.value = reader.result;
        textInput(document);
    }
}

function Edit(document, type, number) {
    if (type === 'task'){
        const Url = "../edit_task";
        var data = new Map();
        data.set("name", getCookie(document, "name"));
        data.set("surname", getCookie(document, "surname"));
        data.set("task", number);
        var request = new XMLHttpRequest();
        request.open("POST", Url + query(data));
        request.send();
        request.onreadystatechange = function() {
            if (this.readyState === 4 && this.status === 200) {
                var answer = this.responseText;
                if (answer !== "Fail"){
                    document.getElementById("content").innerHTML = answer;
                }
                else{
                    alert("Ошибка доступа");
                }
            }
        };
    }
    if (type === 'contest'){
        const Url = "../edit_contest";
        var data = new Map();
        data.set("name", getCookie(document, "name"));
        data.set("surname", getCookie(document, "surname"));
        data.set("contest", number);
        var request = new XMLHttpRequest();
        request.open("POST", Url + query(data));
        request.send();
        request.onreadystatechange = function() {
            if (this.readyState === 4 && this.status === 200) {
                var answer = this.responseText;
                if (answer !== "Fail"){
                    document.getElementById("content").innerHTML = answer;
                }
                else{
                    alert("Ошибка доступа");
                }
            }
        };
    }
}

function Check(document, number) {
    const Url = "../check";
    var data = new Map();
    data.set("name", getCookie(document, "name"));
    data.set("surname", getCookie(document, "surname"));
    data.set("password", getCookie(document, "password"));
    data.set("contest", number);
    var request = new XMLHttpRequest();
    request.open("POST", Url + query(data), false);
    request.send();
    if (request.readyState === 4 && request.status === 200) {
        var answer = request.responseText;
        if (answer === "Fail"){
            document.location.replace("../" + number + ".jsp");
            alert("Неверный пароль или контест не идёт.");
        }
    }
}

function CreateContest(document) {
    var Url = "create_contest";
    var data = new Map();
    data.set("name", getCookie(document, "name"));
    data.set("surname", getCookie(document, "surname"));
    var request = new XMLHttpRequest();
    request.open("POST", Url + query(data));
    request.send();
    request.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var answer = this.responseText;
            if (answer !== "Fail") {
                document.getElementById("content").innerHTML = answer;
            } else {
                alert("Ошибка доступа");
            }
        }
    };
}