"use strict";

let schedulesHtml;
let paginationHtml;
let logout_button = document.getElementById('logout_button');
let urgentClass = 'uk-text-danger';

async function getSchedules(page = 0, size = 10) {
    loadAnimation('schedules');

    schedulesHtml = '';
    paginationHtml = '';
    const url = baseUrl + `/schedules/notCompleted/${page}/${size}`;

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
                writeSchedulesToTable(schedulesPage);
                writePaginationForSchedules(schedulesPage, size);
            });
        unloadAnimation('schedules');
        document.getElementById('schedules').innerHTML = schedulesHtml;
        document.getElementsByClassName('pagination')[0].innerHTML = paginationHtml;

    }

    function writeSchedulesToTable(page) {
        const schedulesTableHeaders =
            `<div class="uk-padding-small">
                <div class="uk-tile uk-tile-muted uk-padding-remove">
                    <p class="uk-h4">Total tasks: ${page.totalElements}
                    <span class="uk-badge .uk-position-right">${page.pageable.pageNumber + 1}|${page.totalPages}</span>
<!--                    <input  onchange="" type="checkbox" checked>Toggle Done</input>-->
                    </p>
<!--                </div>         -->
             </div>   
             <div>
                 <div class="uk-container">                  
                    <span class="uk-label uk-label-warning" uk-icon="location" id="gps_location">Getting location...</span>
                 </div>
             </div>
            <div class="uk-overflow-auto">
            <table class="uk-table uk-table-hover uk-table-middle uk-table-divider">
                <thead>
                    <tr>
<!--                        <th class="uk-table-shrink">Priority</th>-->
                        <th class="uk-width-small">Terminal</th>
                        <th class="uk-width-small">Location</th>
<!--                        <th class="uk-width-small">Task</th>-->
<!--                        <th class="uk-width-small">Status</th>-->
<!--                        <th class="uk-width-small">User</th>-->
                        <th class="uk-width-small">Issued</th>
<!--                        <th class="uk-width-small">Completed</th>-->
                    </tr>
                </thead>
                <tbody>`
        const schedulesTableFooter =
            ``

        page.content.forEach(
            schedule => {
                schedulesHtml +=
                    `<tr>
                        <td class="uk-table-link ${schedule[0].task.priority === 'URGENT' ? urgentClass : ''}">
                            <a class="uk-link-reset" href="single_schedule.html#${schedule.id}">${schedule[0].terminal.name}</a>
                        </td>
                        <td class="uk-table-shrink ${schedule[0].task.priority === 'URGENT' ? urgentClass : ''}">${schedule[0].terminal.location}</td>
                     <!--                         <td class="uk-text-reset">${schedule[0].task.description}</td>-->
                      <!--                        <td class="uk-text-truncate">${schedule[0].status.toString()}</td>-->
                      <!--                        <td class="uk-text-truncate">${schedule[0].user.firstName} ${schedule[0].user.lastName}</td>-->
                        <td class="uk-link-truncate ${schedule[0].task.priority === 'URGENT' ? urgentClass : ''}">${moment(schedule[0].dateTimeCreated).format('DD.MM.YYYY HH:MM')}</td>
                      <!--    <td class="uk-text-truncate">${schedule[0].endExecutionDateTime != null ? schedule[0].endExecutionDateTime : "NOT YET!"}</td>-->
                    </tr>`
            });
        schedulesHtml = schedulesTableHeaders + schedulesHtml + schedulesTableFooter;
        return schedulesHtml;
    }
}

logout_button.addEventListener('click', logout);

getSchedules();
getLocation();