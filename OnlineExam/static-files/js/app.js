window.questionNumber = 1;
var mappingQuestionToAnswer = {}, checkedValue = "", userName = "", startTime, endTime;


function submitForm() {

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
            document.getElementById("divId").innerHTML = xmlHttp.responseText;
            console.log(xmlHttp.responseText);
        }
    }
    userName = document.getElementById("userId").value;
    var date1 = new Date();
    startTime = date1.getTime();
    console.log("Start Time : " + startTime);
    if (userName == "") {
        document.getElementById("userId").focus();
    }
    else {
        xmlHttp.open("GET", "/getFormValue?UserName=" + userName, true);
        xmlHttp.send();
    }
}

function checkBeforeUpdating() {

    if (mappingQuestionToAnswer[window.questionNumber] !== undefined) {

        console.log("Modifying the answer now!!! The previous answer for " + window.questionNumber + " was " + mappingQuestionToAnswer[window.questionNumber]);
        for (i = 0; i < document.getElementsByName('Choice').length; i++) {
            if (document.getElementsByName('Choice')[i].value === mappingQuestionToAnswer[window.questionNumber]) {
                document.getElementsByName('Choice')[i].checked = true;
            }
        }
    }
}

function determineWhichRadioButtonIsChecked() {

    var radios = document.getElementsByTagName('input');
    console.log("No. of radio buttons" + radios.length);
    for (var i = 0; i < radios.length; i++) {
        if (radios[i].type === 'radio' && radios[i].checked) {
            checkedValue = radios[i].value;
            console.log(i + " " + radios[i].value);
            mappingQuestionToAnswer[window.questionNumber] = checkedValue;
        }
    }
}

function nextHandler() {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function () {

        if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
            document.getElementById("divId").innerHTML = xmlHttp.responseText;
            checkBeforeUpdating();
            console.log(xmlHttp.responseText);
        }
    }
    determineWhichRadioButtonIsChecked();

    console.log("In nextHandler() : " + window.questionNumber + " Checked Value : " + mappingQuestionToAnswer[window.questionNumber]);
    window.questionNumber += 1;
    if (window.questionNumber === 5) {
        xmlHttp.open("GET", "/loadLastQuestion?Question=" + window.questionNumber, true);
        xmlHttp.send();
    }
    else {
        xmlHttp.open("GET", "/loadFromSecondToLastButOne?Question=" + window.questionNumber, true);
        xmlHttp.send();
    }
}

function prevHandler() {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
            document.getElementById("divId").innerHTML = xmlHttp.responseText;
            checkBeforeUpdating();
            console.log(xmlHttp.responseText);
        }
    }
    determineWhichRadioButtonIsChecked();

    console.log("In prevHandler() : " + window.questionNumber + " Checked Value : " + mappingQuestionToAnswer[window.questionNumber]);
    window.questionNumber -= 1;
    if (window.questionNumber === 1) {
        xmlHttp.open("GET", "/getFormValue", true);
        xmlHttp.send();
    }

    else {
        xmlHttp.open("GET", "/loadFromSecondToLastButOne?Question=" + window.questionNumber, true);
        xmlHttp.send();
    }
}

function computeResult() {
    var xmlHttp = new XMLHttpRequest();


    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
            document.getElementById("divId").innerHTML = xmlHttp.responseText;
            console.log(xmlHttp.responseText);
        }
    }
    determineWhichRadioButtonIsChecked();

    for (var i = 1; i <= 5; i++) {
        console.log(mappingQuestionToAnswer[i] + "\n");
    }
    var date2 = new Date();
    endTime = date2.getTime();
    console.log("End Time : " + endTime);
    var duration = endTime - startTime;
    console.log("Duration : " + duration);
    xmlHttp.open("GET", "/computeResult?UserName=" + userName + "&Ans1=" + mappingQuestionToAnswer[1] + "&Ans2=" + mappingQuestionToAnswer[2] + "&Ans3="
        + mappingQuestionToAnswer[3] + "&Ans4=" + mappingQuestionToAnswer[4] + "&Ans5=" + mappingQuestionToAnswer[5] + "&Time=" + duration, true);
    xmlHttp.send();
}

