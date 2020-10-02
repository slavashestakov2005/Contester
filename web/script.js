function getCookie(document, name) {
    alert("Вызвана")
    var r = document.cookie.match("(^|;) ?" + name + "=([^;]*)(;|$)");
    if (r) return r[2].trim();
    else return "";
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