"use strict";

let schedulesHtml;
let paginationHtml;
let logoutButton = document
    .getElementById('logout_button');
document.getElementById('current_user')
    .innerHTML =
    `<div class="uk-alert-primary uk-alert">${getCurrentUserEmail(parseToken(getToken()))}</div>`;

async function getSchedules(page = 0,
                            size = 5) {
    loadAnimation('schedules');

    schedulesHtml = '';
    paginationHtml = '';
    const url = baseUrl +
        `/schedules/notCompletedSortedByPriorityIndex/${page}/${size}`;

    if (checkTechRights(parseToken(
        getToken()))) {
        await fetch(url, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Authorization': `Bearer ${getToken()}`
            },
        })
            .then((response) => {
                return response
                    .json();
            })
            .then(schedulesPage => {
                writeSchedulesToTable(schedulesPage);
                writePaginationForSchedules(schedulesPage, size);
            })
            .catch(() => {
                schedulesHtml =
                    NO_SCHEDULES;
            })
        unloadAnimation(
            'schedules');
        document.getElementById(
            'schedules')
            .innerHTML =
            schedulesHtml;
        document
            .getElementsByClassName(
                'pagination')[0]
            .innerHTML =
            paginationHtml;
    }

    function writeSchedulesToTable(
        p) {
        let schedulesTableHeaders =
            `<div class="uk-padding-small">
                <div class="uk-tile uk-tile-muted uk-padding-remove">
                    <p class="uk-h4">Total tasks: ${p.totalElements}
                    <span class="uk-badge .uk-position-right">${p.pageable.pageNumber + 1}|${p.totalPages}</span>
                    </p>
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
                        <th class="uk-width-small">Terminal</th>
                        <th class="uk-width-small">Location</th>
                        <th class="uk-width-small">Issued</th>
                    </tr>
                </thead>
                <tbody>`
        let schedulesTableFooter =
            ``
        if (!p.empty) {
            p.content.forEach(
                schedule => {
                    schedulesHtml
                        +=
                        `<tr> 
                        <td class="uk-table-link ${schedule[0].task.priority === 'URGENT' ? URGENT_TASK_CLASS : ''}">
                            <a class="uk-link-reset" href="single_schedule.html#${schedule[0].id}">${schedule[0].terminal.name}</a>
                        </td>
                        <td class="uk-table-shrink ${schedule[0].task.priority === 'URGENT' ? URGENT_TASK_CLASS : ''}">${schedule[0].terminal.location}</td>
                     <!--                         <td class="uk-text-reset">${schedule[0].task.description}</td>-->
                      <!--                        <td class="uk-text-truncate">${schedule[0].status.toString()}</td>-->
                      <!--                        <td class="uk-text-truncate">${schedule[0].user.firstName} ${schedule[0].user.lastName}</td>-->
                        <td class="uk-link-truncate ${schedule[0].task.priority === 'URGENT' ? URGENT_TASK_CLASS : ''}">${moment(schedule[0].dateTimeCreated).format('DD.MM.YYYY HH:MM')}</td>
                      <!--    <td class="uk-text-truncate">${schedule[0].endExecutionDateTime != null ? schedule[0].endExecutionDateTime : "NOT YET!"}</td>-->
                    </tr>`
                });
        } else {
            schedulesTableHeaders =
                '';
            schedulesTableFooter =
                '';
            schedulesHtml =
                NO_SCHEDULES;
        }
        schedulesHtml =
            schedulesTableHeaders +
            schedulesHtml +
            schedulesTableFooter;
        return schedulesHtml;
    }
}

logoutButton.addEventListener('click',
    logout);

getSchedules();
getLocation();