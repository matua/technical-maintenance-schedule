"use strict";

let edit_task_form = document.getElementById('edit_task_form');
let queryString = location.search.substring(1);
let arrayOfTaskParameters = queryString.split("|");
document.getElementById('task_description').value = arrayOfTaskParameters[0];
document.getElementById('task_priority').value = arrayOfTaskParameters[1];
document.getElementById('task_frequency').value = arrayOfTaskParameters[2];

async function editTask() {
    // event.preventDefault();
    // darkenScreen();
    const form = new FormData(edit_task_form);
    let createTaskDtoFromForm = fromFormDataToJson(form);
    createTaskDtoFromForm.description =;

    const url = baseUrl + `/admin/tasks`;

    if (checkAdminRights(parseToken(getToken()))) {
        await fetch(url, {
            method: 'PUT',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
                'Accept': 'application/json',
                'Authorization': `Bearer ${getToken()}`
            },
            body: JSON.stringify(createTaskDtoFromForm)
        });
    }
}