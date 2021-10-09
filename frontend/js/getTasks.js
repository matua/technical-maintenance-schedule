"use strict";

let tasksHtml;
let logout_button = document.getElementById('logout_button');

async function getTasks(page = 0, size = 10) {
    tasksHtml = '';
    const url = baseUrl + `/tasks/${page}/${size}`;

    if (checkAdminRights(parseToken(getToken()))) {
        await fetch(url, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Authorization': `Bearer ${getToken()}`
            },
        })
            .then((response) => {
                return response.json();
            })
            .then(tasksPage => {
                writeTasksToTable(tasksPage);
            });
        document.getElementById('tasks').innerHTML = tasksHtml;
    }

    function writeTasksToTable(page) {
        const tasksTableHeaders =
            `<div class="uk-overflow-auto">
            <table class="uk-table uk-table-hover uk-table-middle uk-table-divider">
                <thead>
                    <tr>
                        <th class="uk-table-shrink">Priority</th>
                        <th class="uk-table-expand">Description</th>
                        <th class="uk-width-small">Frequency</th>
                    </tr>
                </thead>
                <tbody>`
        const tasksTableFooter =
            `</tbody>
       </table>`
        page.content.forEach(
            task => {
                let taskPriorityIcon = taskStatusIcon(task.priority);
                tasksHtml +=
                    `<tr>
                        <td><img class="uk-preserve-width uk-border-circle" src="images/${taskPriorityIcon}" width="40" alt=""></td>
                        <td class="uk-table-link">
                            <a class="uk-link-reset" href="">${task.description}</a>
                        </td>
                        <td class="uk-text-truncate">${task.frequency}</td>
                    </tr>`
            });
        tasksHtml = tasksTableHeaders + tasksHtml + tasksTableFooter;
        return tasksHtml;
    }
}

logout_button.addEventListener('click', logout);

getTasks();
