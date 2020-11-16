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

function onServerAnswer(){
    if (this.readyState === 4 && this.status === 200) {
        var answer = this.responseText;
        if (answer === "Ok") {
            alert("Изменения сохранены");
        } else {
            alert("Ошибка доступа");
        }
    }
}

function splitDateTime(datetime){
    return new Date(datetime).getTime();
}

var minTime = "2020-10-21T19:50", maxTime = "9999-12-31T23:59";
var minTimeSplit = splitDateTime(minTime);
var maxTimeSplit = splitDateTime(maxTime);
minTime = "21.10.2020 19:50"; maxTime = "31.12.9999 23:59";

function checkTime(time) {
    time = splitDateTime(time);
    return minTimeSplit <= time && time <= maxTimeSplit;
}

function checkPassword(password){
    return /^[\w_\+\-\*\/]+$/.test(password);
}


function NewRow(document, cnt){
    var row = document.createElement("tr");
    var change = 'Change(document, ' + cnt + ');';
    row.id = "" + cnt;
    document.getElementById("task").appendChild(row);
    var td1 = document.createElement("td");
    var td2 = document.createElement("td");
    var td3 = document.createElement("td");
    var td4 = document.createElement("td");
    var td5 = document.createElement("td");
    var td6 = document.createElement("td");
    var td7 = document.createElement("td");
    var td8 = document.createElement("td");
    row.appendChild(td1);
    row.appendChild(td2);
    row.appendChild(td3);
    row.appendChild(td4);
    row.appendChild(td5);
    row.appendChild(td6);
    row.appendChild(td7);
    row.appendChild(td8);
    td1.innerHTML = '' + cnt;
    td2.innerHTML = '<p id="index'          + cnt + '">-1</p>';
    td3.innerHTML = '<textarea id="input'	+ cnt + '"class="input_output" oninput="' + change + '"></textarea>';
    td4.innerHTML = '<textarea id="output' 	+ cnt + '" class="input_output" oninput="' + change + '"></textarea>';
    td5.innerHTML = '<input id="example' 	+ cnt + '" type="checkbox" onchange="' + change + '">';
    td6.innerHTML = '<input id="public' 	+ cnt + '" type="checkbox" onchange="' + change + '">';
    td7.innerHTML = '<button id="btn' 		+ cnt + '">Изменено</button>';
    td8.innerHTML = '<button onclick="DeleteTest(document, -1)">Удалить</button>';
}

function Change(document, cnt){
    document.getElementById("btn" + cnt).disabled = false;
}

function Save(document, cnt, type, number){
    if (type === 'task') {
        var Url = "../tasks";
        var data = new Map();
        data.set("name", getCookie(document, "name"));
        data.set("surname", getCookie(document, "surname"));
        data.set("id", number);
        data.set("t_name", document.getElementById("task_name").value);
        data.set("t_about", document.getElementById("task_description").value);
        data.set("t_input", document.getElementById("task_input").value);
        data.set("t_output", document.getElementById("task_output").value);
        data.set("t_solution", document.getElementById("task_solution").value);
        var request = new XMLHttpRequest();
        request.open("POST", Url + query(data));
        request.send();
        request.onreadystatechange = onServerAnswer;

        Url = "../tests";
        for (var i = 1; i <= cnt; ++i) {
            if (document.getElementById("btn" + i).disabled === false) {
                var data = new Map();
                data.set("input", document.getElementById("input" + i).value);
                data.set("output", document.getElementById("output" + i).value);
                data.set("example", document.getElementById("example" + i).checked);
                data.set("public", document.getElementById("public" + i).checked);
                data.set("task", number);
                data.set("test", document.getElementById("index" + i).textContent);
                data.set("name", getCookie(document, "name"));
                data.set("surname", getCookie(document, "surname"));
                var request = new XMLHttpRequest();
                request.open("POST", Url + query(data));
                request.send();
                document.getElementById("btn" + i).disabled = true;
            }
        }
    }
    if (type === 'contest'){
        var Url = "../contests";
        var start = document.getElementById("start_datetime").value;
        var finish = document.getElementById("finish_datetime").value;
        var password = document.getElementById("contest_password").value;
        if (!checkTime(start) || !checkTime(finish)){
            alert("Ошибка заполнения полей времени. Значения должны располагаться в диапазоне от " + minTime + " до " + maxTime + ".");
            return;
        }
        if (!checkPassword(password)){
            alert("Пароль должен содержать только буквы, цифры и _ + - * /");
            return;
        }
        var data = new Map();
        data.set("name", getCookie(document, "name"));
        data.set("surname", getCookie(document, "surname"));
        data.set("id", number);
        data.set("t_name", document.getElementById("task_name").value);
        data.set("t_about", document.getElementById("task_description").value);
        data.set("start", splitDateTime(start));
        data.set("finish", splitDateTime(finish));
        data.set("password", password);
        var request = new XMLHttpRequest();
        request.open("POST", Url + query(data));
        request.send();
        request.onreadystatechange = onServerAnswer;
    }
}

function Delete(document, task, number){
    var Url = "../delete_task";
    var data = new Map();
    data.set("name", getCookie(document, "name"));
    data.set("surname", getCookie(document, "surname"));
    data.set("task", task);
    data.set("contest", number);
    var request = new XMLHttpRequest();
    request.open("POST", Url + query(data));
    request.send();
    request.onreadystatechange = onServerAnswer;
}

function DeleteContest(document, number){
    var Url = "../delete_contest";
    var doDelete = prompt("Вы действительно хотите удалить?\nДля удаления введите \"да\".");
    if (doDelete !== "да") return;
    var data = new Map();
    data.set("name", getCookie(document, "name"));
    data.set("surname", getCookie(document, "surname"));
    data.set("contest", number);
    var request = new XMLHttpRequest();
    request.open("POST", Url + query(data));
    request.send();
    request.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var answer = this.responseText;
            if (answer === "Ok") {
                alert("Изменения сохранены");
                document.location.replace("../main.jsp");
            } else {
                alert("Ошибка доступа");
            }
        }
    };
}

function DeleteTest(document, number){
    var test = document.getElementById("index" + number).textContent;
    if (test === -1) return;
    var Url = "../delete_test";
    var data = new Map();
    data.set("name", getCookie(document, "name"));
    data.set("surname", getCookie(document, "surname"));
    data.set("test", test);
    var request = new XMLHttpRequest();
    request.open("POST", Url + query(data));
    request.send();
    request.onreadystatechange = onServerAnswer;
}

function Create(document){
    var Url = "../create_task";
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

function AddTask(document, contestId) {
    var Url = "../add_task";
    var data = new Map();
    data.set("name", getCookie(document, "name"));
    data.set("surname", getCookie(document, "surname"));
    data.set("contest", contestId);
    data.set("t_name", document.getElementById("task_name").value);
    data.set("t_about", document.getElementById("task_description").value);
    data.set("t_input", document.getElementById("task_input").value);
    data.set("t_output", document.getElementById("task_output").value);
    data.set("t_solution", document.getElementById("task_solution").value);
    var request = new XMLHttpRequest();
    request.open("POST", Url + query(data));
    request.send();
    request.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var answer = this.responseText;
            if (answer === "Ok") {
                alert("Изменения сохранены");
                document.location.replace("contest.jsp");
            } else {
                alert("Ошибка доступа");
                document.location.replace("contest.jsp");
            }
        }
    };
}


function AddContest(document) {
    var start = document.getElementById("start_datetime").value;
    var finish = document.getElementById("finish_datetime").value;
    if (!checkTime(start) || !checkTime(finish)){
        alert("Ошибка заполнения полей времени. Значения должны располагаться в диапазоне от " + minTime + " до " + maxTime + ".");
        return;
    }
    var Url = "add_contest";
    var data = new Map();
    data.set("name", getCookie(document, "name"));
    data.set("surname", getCookie(document, "surname"));
    data.set("c_name", document.getElementById("contest_name").value);
    data.set("c_about", document.getElementById("contest_description").value);
    data.set("start", splitDateTime(start));
    data.set("finish", splitDateTime(finish));
    data.set("password", document.getElementById("password").value);
    var request = new XMLHttpRequest();
    request.open("POST", Url + query(data));
    request.send();
    request.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var answer = this.responseText;
            if (answer === "Ok") {
                alert("Изменения сохранены");
                document.location.replace("main.jsp");
            } else {
                alert("Ошибка доступа");
                document.location.replace("main.jsp");
            }
        }
    };
}

function Generate(document, number){
    var Url = "../generate";
    var data = new Map();
    data.set("name", getCookie(document, "name"));
    data.set("surname", getCookie(document, "surname"));
    data.set("contest", number);
    var request = new XMLHttpRequest();
    request.open("POST", Url + query(data));
    request.send();
    request.onreadystatechange = onServerAnswer;
}