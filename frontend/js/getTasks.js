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
    } else {
        tasksHtml = `<div class="uk-alert-danger uk-position-center uk-alert">
                                <a class="uk-alert-close"></a>
                                <p>Not authorized!</p>`;
    }
    document.getElementById('tasks').innerHTML = tasksHtml;


    function writeTasksToTable(p) {
        const tasksTableHeaders =
            `<a href="edit_task.html" uk-icon="icon: plus" class="uk-padding-large">Add new    </a>
            <div class="uk-overflow-auto">
            <table class="uk-table uk-table-hover uk-table-middle uk-table-divider">
                <thead>
                    <tr>
                        <th class="uk-table-shrink">Priority</th>
                        <th class="uk-table-expand">Description</th>
                        <th class="uk-width-small">Frequency</th>
                        <th class="uk-width-small"></th>
                        <th class="uk-width-small"></th>
                        <th class="uk-width-small"></th>
                    </tr>
                </thead>
                <tbody>`
        const tasksTableFooter =
            `</tbody>
       </table>`
        p.content.forEach(
            task => {
                let taskPriorityIcon = taskStatusIcon(task.priority);
                tasksHtml +=
                    `<tr>
                        <td><img class="uk-preserve-width uk-border-circle" src="images/${taskPriorityIcon}" width="40" alt=""></td>
                        <td class="uk-table-link">
                            <a class="uk-link-reset">${task.description}</a>
                        </td>
                        <td class="uk-text-truncate">${task.frequency}</td>
                        <td class="uk-text-truncate">
                        <a href="edit_task.html?${task.description}|${task.priority}|${task.frequency}"
                         class="uk-icon-link" uk-icon="pencil"></a></td>    
                         <td class="uk-text-truncate">    
                        <a class="uk-icon-link" uk-icon="minus-circle" onclick="deleteTask(${task.id})"></a></td>
                    </tr>`
            });
        tasksHtml = tasksTableHeaders + tasksHtml + tasksTableFooter;
        return tasksHtml;
    }
}

logout_button.addEventListener('click', logout);

getTasks();
getLocation();

async function deleteTask(id) {
    duDialog(null, 'This action cannot be undone, proceed?', {
        buttons: duDialog.OK_CANCEL,
        okText: 'Delete Task',
        callbacks: {
            okClick: async function () {
                const url = baseUrl + `/admin/tasks/${id}`;
                if (checkAdminRights(parseToken(getToken()))) {
                    await fetch(url, {
                        method: 'DELETE',
                        headers: {
                            'Accept': 'application/json',
                            'Authorization': `Bearer ${getToken()}`
                        },
                    });
                }
                this.hide();
                document.location.reload();
            }
        }
    });
}
