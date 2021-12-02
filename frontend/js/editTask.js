"use strict";

let edit_task_form = document.getElementById('edit_task_form');
let queryString = location.search.substring(1);
let arrayOfTaskParameters = queryString.split("|");
document.getElementById('description').value = decodeURI(arrayOfTaskParameters[0]);
document.getElementById('priority').value = decodeURI(arrayOfTaskParameters[1]);
document.getElementById('frequency').value = decodeURI(arrayOfTaskParameters[2]);
let newOrUpdate;
if (arrayOfTaskParameters[0].length === 0) {
    newOrUpdate = 'POST';
} else {
    newOrUpdate = 'PUT';
}
console.log(newOrUpdate);

async function editTask() {
    const form = new FormData(edit_task_form);
    let TaskDtoToUpdateFromForm = fromFormDataToJson(form);

    const url = baseUrl + `/admin/tasks`;

    if (checkAdminRights(parseToken(getToken()))) {
        await fetch(url, {
            method: newOrUpdate,
            body: JSON.stringify(TaskDtoToUpdateFromForm),
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
                'Accept': 'application/json',
                'Authorization': `Bearer ${getToken()}`
            },
        });
    }
    window.location.replace('../tasks.html');
}

edit_task_form.addEventListener('submit', editTask);