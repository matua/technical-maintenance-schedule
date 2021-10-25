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
                    
                       <h3 class="uk-card-title"><a href="schedules.html">${page.terminal.name}</a> </h3></br>
                       <p>Location: <span class="uk-badge">${page.terminal.location}</span></br>
                        Description: <span class="uk-badge">${page.task.description}</span></br>
                        Status:<span class="uk-badge"> ${page.status.toString()}</span></br>
                       Time Created:<span class="uk-badge"> ${convertFromJavaToJavascriptTime(page.dateTimeCreated)}</span></br>
                       Time Completed:<span class="uk-badge"> ${page.endExecutionDateTime != null ? schedule.endExecutionDateTime : "NOT YET!"}</span></br>
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