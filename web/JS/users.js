function getCookie(document, name) {
    var r = document.cookie.match("(^|;) ?" + name + "=([^;]*)(;|$)");
    if (r) return r[2].trim();
    else return "";
}

function query(parameters){
    var result = "?";
    parameters.forEach((value, key) => {
        result += key + '=' + value + "&";
    });
    return result;
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

function sendCode(code) {
    alert(code);
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

function Edit(document, name) {
    const Url = "edit_tests";
    if (name !== 'contest'){
        var data = new Map();
        data.set("name", getCookie(document, "name"));
        data.set("surname", getCookie(document, "surname"));
        data.set("task", name);
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