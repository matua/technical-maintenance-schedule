"use strict";

let singleScheduleHtml;
let logout_button = document.getElementById('logout_button');

async function getSingleSchedule() {
    let currentUrl = window.location.href;
    let scheduleId = currentUrl.substr(currentUrl.indexOf('#') + 1);
    singleScheduleHtml = '';
    const url = baseUrl + `/schedules/${scheduleId}`;

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
            .then(singleSchedulePage => {
                writeSingleScheduleToTable(singleSchedulePage);
            });
        document.getElementById('schedule').innerHTML = singleScheduleHtml;
    }

    function writeSingleScheduleToTable(page) {
        let start_task_button;
        let complete_task_button;
        if (page.startExecutionDateTime == null) {
            start_task_button = `<button id="start_task_button" onclick="startTask(${page.id})" class="uk-button uk-button-secondary uk-button-large">START TASK</button>`
        } else {
            start_task_button = '';
        }
        if (page.endExecutionDateTime == null && page.startExecutionDateTime != null) {
            complete_task_button = `<button id="complete_task_button" onclick="completeTask(${page.id})" class="uk-button uk-button-secondary uk-button-large">COMPLETE TASK</button>`
        } else {
            complete_task_button = '';
        }

        const singleScheduleTableHeaders =

            `<span class="uk-badge" uk-icon="location" id="gps_location">Getting location...</span>
        <div class="uk-overflow-auto">
                <thead>
                </thead>
                <tbody>`
        const singleScheduleTableFooter =
            `
            `
        singleScheduleHtml +=
            `
                <div class="uk-card uk-card-default uk-card-body uk-width-1-2@s">                                          
                       <span class=".uk-label-success"><a class="uk-link-muted" href="schedules.html">${page.terminal.name}</a></span></br>
                       <p><span uk-icon="location"></span>   ${page.terminal.location}</br></br>
                        <i>${page.task.description}</i></br></br>
                        Status: <span class="uk-badge"> ${page.status.toString()}</span></br>
                       Time Created: <code> ${moment(convertFromJavaToJavascriptTime(page.dateTimeCreated)).format('DD/MM/YY HH:MM')}</code></br>
                       Time Completed: <code> ${page.endExecutionDateTime != null ? moment(new Date(page.endExecutionDateTime)).format('DD/MM/YY HH:MM') : "NOT YET!"}</code></br>
                       <p uk-margin>
                       ${start_task_button}
                       </p>  
                           <p uk-margin>
                       ${complete_task_button}
                       </p>
                    Time Spent on task: <code>${timeDifference(new Date(page.endExecutionDateTime), new Date(page.startExecutionDateTime))}</code>
                        </p>
                </div>
            `
        singleScheduleHtml = singleScheduleTableHeaders + singleScheduleHtml + singleScheduleTableFooter;
        return singleScheduleHtml;
    }
}

logout_button.addEventListener('click', logout);

getSingleSchedule();
getLocation();

async function completeTask(id) {
    if (confirm("Please confirm")) {
        const url = baseUrl + `/admin/schedules/complete/${id}`;
        if (checkAdminRights(parseToken(getToken()))) {
            await fetch(url, {
                method: 'PUT',
                headers: {
                    'Accept': 'application/json',
                    'Authorization': `Bearer ${getToken()}`
                },
            });
        }
        let complete_task_button = document.getElementById('complete_task_button');
        complete_task_button.hidden = true;
        document.location.reload();
    }
}

async function startTask(id) {
    if (confirm("Please confirm")) {
        const url = baseUrl + `/admin/schedules/start/${id}`;
        if (checkAdminRights(parseToken(getToken()))) {
            await fetch(url, {
                method: 'PUT',
                headers: {
                    'Accept': 'application/json',
                    'Authorization': `Bearer ${getToken()}`
                },
            });
        }
        let start_task_button = document.getElementById('start_task_button');
        start_task_button.hidden = true;
        document.location.reload();
    }
}

function timeDifference(date1, date2) {
    if ((Date.now() - date1) > 3.154e10) {
        return '';
    }
    let difference = date1.getTime() - date2.getTime();

    const daysDifference = Math.floor(difference / 1000 / 60 / 60 / 24);
    difference -= daysDifference * 1000 * 60 * 60 * 24

    const hoursDifference = Math.floor(difference / 1000 / 60 / 60);
    difference -= hoursDifference * 1000 * 60 * 60

    const minutesDifference = Math.floor(difference / 1000 / 60);
    difference -= minutesDifference * 1000 * 60

    const secondsDifference = Math.floor(difference / 1000);

    return `
        ${daysDifference === 0 ? '' : daysDifference + ' day/s'} 
        ${hoursDifference === 0 ? '' : hoursDifference + ' hour/s'}
        ${minutesDifference === 0 ? '' : minutesDifference + ' minute/s'}
        ${secondsDifference === 0 ? '' : secondsDifference + ' second/s'}
        `;
}
