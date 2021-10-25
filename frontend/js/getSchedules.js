"use strict";

let schedulesHtml;
let logout_button = document.getElementById('logout_button');

async function getSchedules(page = 0, size = 1000) {
    schedulesHtml = '';
    const url = baseUrl + `/schedules/${page}/${size}`;

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
            .then(schedulesPage => {
                writeSchedulessToTable(schedulesPage);
            });
        document.getElementById('schedules').innerHTML = schedulesHtml;
    }

    function writeSchedulessToTable(page) {
        const schedulesTableHeaders =
            `Total schedules:             
<span class="uk-badge">${page.totalElements}</span>
<span class="uk-badge" id="gps_location"></span>
<div class="uk-overflow-auto">
            <table class="uk-table uk-table-hover uk-table-middle uk-table-divider">
                <thead>
                    <tr>
<!--                        <th class="uk-table-shrink">Priority</th>-->
                        <th class="uk-width-small">Terminal</th>
                        <th class="uk-width-small">Location</th>
                        <th class="uk-width-small">Task</th>
                        <th class="uk-width-small">Status</th>
                        <th class="uk-width-small">User</th>
                        <th class="uk-width-small">Started</th>
                        <th class="uk-width-small">Completed</th>
                    </tr>
                </thead>
                <tbody>`
        const schedulesTableFooter =
            `</tbody>
       </table>`
        page.content.forEach(
            schedule => {
                // let taskPriorityIcon = taskStatusIcon(schedule.priority);
                schedulesHtml +=
                    `<tr>
                        <td class="uk-table-link">
                            <a class="uk-link-reset" href="">${schedule.terminal.name}</a>
                        </td>
                        <td class="uk-text-truncate">${schedule.terminal.location}</td>
                        <td class="uk-text-reset">${schedule.task.description}</td>
                        <td class="uk-text-truncate">${schedule.status.toString()}</td>
                        <td class="uk-text-truncate">${schedule.user.firstName} ${schedule.user.lastName}</td>
                        <td class="uk-link-truncate">${convertFromJavaToJavascriptTime(schedule.dateTimeCreated)}</td>
                        <td class="uk-text-truncate">${schedule.endExecutionDateTime != null ? schedule.endExecutionDateTime : "NOT YET!"}</td>
                    </tr>`
            });
        schedulesHtml = schedulesTableHeaders + schedulesHtml + schedulesTableFooter;
        return schedulesHtml;
    }
}

logout_button.addEventListener('click', logout);

getSchedules();
getLocation();