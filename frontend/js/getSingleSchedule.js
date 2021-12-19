"use strict";

let singleScheduleHtml;
const userRole = getCurrentUserRole(parseToken(getToken()));

async function getSingleSchedule() {
    loadAnimation('schedule');
    let currentUrl = window.location.href;
    let scheduleId = currentUrl.substr(currentUrl.indexOf('#') + 1);
    singleScheduleHtml = '';
    const url = baseUrl + `/schedules/${scheduleId}`;

    if (checkAdminOrTechRights(parseToken(getToken()))) {
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
        unloadAnimation('schedule');
        document.getElementById('schedule').innerHTML = singleScheduleHtml;
    }

    function writeSingleScheduleToTable(page) {
        let start_task_button;
        let complete_task_button;
        let grab_task_button;
        let release_task_button;
        if (userRole === 'TECHNICIAN') {
            if (page.startExecutionDateTime == null && page.grabbedExecutionDateTime != null) {
                start_task_button = `<button id="start_task_button" onclick="startTask(${page.id})" class="uk-button uk-button-secondary uk-button-large">START TASK</button>`
            } else {
                start_task_button = '';
            }
            if (page.endExecutionDateTime == null && page.startExecutionDateTime != null) {
                complete_task_button = `<button id="complete_task_button" onclick="completeTask(${page.id})" class="uk-button uk-button-secondary uk-button-large">COMPLETE TASK</button>`
            } else {
                complete_task_button = '';
            }
            if (page.grabbedExecutionDateTime == null && page.startExecutionDateTime == null) {
                grab_task_button = `<button id="grab_task_button" onclick="grabTask(${page.id})" class="uk-button uk-button-secondary uk-button-large">GRAB TASK</button>`
            } else {
                grab_task_button = '';
            }
            if (page.grabbedExecutionDateTime != null && page.startExecutionDateTime == null) {
                release_task_button = `<button id="release_task_button" onclick="releaseTask(${page.id})" class="uk-button uk-button-secondary uk-button-large">RELEASE TASK</button>`
            } else {
                release_task_button = '';
            }
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
            `<div class="uk-card uk-card-default uk-card-body uk-width-1-2@s">   
                <ul class="uk-breadcrumb">
                    <li><a href="schedules.html">Tap to all Schedules</a></li>
                </ul>                                       
                <span class=".uk-label-success"><a class="uk-link-muted">${page.terminal.name}</a></span></br>
                <p><span uk-icon="location"></span>   ${page.terminal.location}</br></br>
                <i>${page.task.description}</i></br></br>
                Status: <span class="uk-badge"> ${page.status}</span></br>
                Time Issued: <code> ${moment(page.dateTimeCreated).format('DD.MM.YYYY HH:MM')}</code></br>
                Time Grabbed: <code> ${page.grabbedExecutionDateTime != null ? moment(new Date(page.grabbedExecutionDateTime)).format('DD.MM.YYYY HH:MM') : "NOT YET!"}</code></br>
                Time Released: <code> ${page.releasedExecutionDateTime != null ? moment(new Date(page.releasedExecutionDateTime)).format('DD.MM.YYYY HH:MM') : "No"}</code></br>
                Time Started: <code> ${page.startExecutionDateTime != null ? moment(new Date(page.startExecutionDateTime)).format('DD.MM.YYYY HH:MM') : "NOT YET!"}</code></br>
                Time Completed: <code> ${page.endExecutionDateTime != null ? moment(new Date(page.endExecutionDateTime)).format('DD.MM.YYYY HH:MM') : "NOT YET!"}</code></br>
                <p uk-margin>
                ${grab_task_button === undefined ? '' : grab_task_button}
                </p>  
                <p uk-margin>
                ${release_task_button === undefined ? '' : release_task_button}
                </p>
                 <p uk-margin>
                ${start_task_button === undefined ? '' : start_task_button}
                </p>  
                <p uk-margin>
                ${complete_task_button === undefined ? '' : complete_task_button}
                </p>
                Time Spent on task: <code>${timeDifference(new Date(page.endExecutionDateTime), new Date(page.startExecutionDateTime))}</code>
                </p>
            </div>
            `
        singleScheduleHtml = singleScheduleTableHeaders + singleScheduleHtml + singleScheduleTableFooter;
        return singleScheduleHtml;
    }
}

function grabTask(id) {
    duDialog(null, `Let's grab it?`, {
        buttons: duDialog.OK_CANCEL,
        okText: 'Grab Task',
        callbacks: {
            okClick: async function () {
                const url = baseUrl + `/schedules/grab/${id}`;
                if (checkTechRights(parseToken(getToken()))) {
                    await fetch(url, {
                        method: 'PUT',
                        headers: {
                            'Accept': 'application/json',
                            'Authorization': `Bearer ${getToken()}`
                        },
                    });
                }
                this.hide();
                let grab_task_button = document.getElementById('grab_task_button');
                grab_task_button.hidden = true;
                document.location.reload();
            }
        }
    });
}

function releaseTask(id) {
    duDialog(null, `Release the task?`, {
        buttons: duDialog.OK_CANCEL,
        okText: 'Release Task',
        callbacks: {
            okClick: async function () {
                const url = baseUrl + `/schedules/release/${id}`;
                if (checkTechRights(parseToken(getToken()))) {
                    await fetch(url, {
                        method: 'PUT',
                        headers: {
                            'Accept': 'application/json',
                            'Authorization': `Bearer ${getToken()}`
                        },
                    });
                }
                this.hide();
                let release_task_button = document.getElementById('release_task_button');
                release_task_button.hidden = true;
                document.location.reload();
            }
        }
    });
}

function startTask(id) {
    duDialog(null, 'This action cannot be undone, proceed?', {
        buttons: duDialog.OK_CANCEL,
        okText: 'Start Task',
        callbacks: {
            okClick: async function () {
                const url = baseUrl + `/schedules/start/${id}`;
                if (checkTechRights(parseToken(getToken()))) {
                    await fetch(url, {
                        method: 'PUT',
                        headers: {
                            'Accept': 'application/json',
                            'Authorization': `Bearer ${getToken()}`
                        },
                    });
                }
                this.hide();
                let start_task_button = document.getElementById('start_task_button');
                start_task_button.hidden = true;
                document.location.reload();
            }
        }
    });
}

async function completeTask(id) {
    duDialog(null, 'This action cannot be undone, proceed?', {
        buttons: duDialog.OK_CANCEL,
        okText: 'Complete Task',
        callbacks: {
            okClick: async function () {
                const url = baseUrl + `/schedules/complete/${id}`;
                if (checkTechRights(parseToken(getToken()))) {
                    await fetch(url, {
                        method: 'PUT',
                        headers: {
                            'Accept': 'application/json',
                            'Authorization': `Bearer ${getToken()}`
                        },
                    });
                }
                this.hide();
                let complete_task_button = document.getElementById('complete_task_button');
                complete_task_button.hidden = true;
                document.location.reload();
            }
        }
    });
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

logout_button.addEventListener('click', logout);

getSingleSchedule();
getLocation();