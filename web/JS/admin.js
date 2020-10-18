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
    row.appendChild(td1);
    row.appendChild(td2);
    row.appendChild(td3);
    row.appendChild(td4);
    row.appendChild(td5);
    row.appendChild(td6);
    td1.innerHTML = '' + cnt;
    td2.innerHTML = '<textarea id="input'	+ cnt + '"class="input_output" oninput="' + change + '"></textarea>';
    td3.innerHTML = '<textarea id="output' 	+ cnt + '" class="input_output" oninput="' + change + '"></textarea>';
    td4.innerHTML = '<input id="example' 	+ cnt + '" type="checkbox" onchange="' + change + '">';
    td5.innerHTML = '<input id="public' 	+ cnt + '" type="checkbox" onchange="' + change + '">';
    td6.innerHTML = '<button id="btn' 		+ cnt + '">Изменено</button>';
}

function Change(document, cnt){
    document.getElementById("btn" + cnt).disabled = false;
}

function Save(document, cnt){
    const Url = "tests";
    for(var i = 1; i <= cnt; ++i){
        if (document.getElementById("btn" + i).disabled === false){
            var data = new Map();
            data.set("input", document.getElementById("input" + i).value);
            data.set("output", document.getElementById("output" + i).value);
            data.set("example", document.getElementById("example" + i).checked);
            data.set("public", document.getElementById("public" + i).checked);
            data.set("id", i);
            data.set("name", getCookie(document, "name"));
            data.set("surname", getCookie(document, "surname"));
            var request = new XMLHttpRequest();
            request.open("POST", Url + query(data));
            request.send();
            var ok = false;
            request.onreadystatechange = function() {
                if (ok === false && this.readyState === 4 && this.status === 200) {
                    var answer = this.responseText;
                    if (answer === "Ok"){
                        alert("Изменения сохранены");
                        ok = true;
                    }
                    else{
                        alert("Ошибка доступа");
                    }
                }
            };
            document.getElementById("btn" + i).disabled = true;
        }
    }
}