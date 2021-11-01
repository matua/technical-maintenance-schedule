"use strict";

function fromFormDataToJson(formData) {
    let json = {};
    formData.forEach((value, key) => {
        json[key] = value;
    });
    return json;
}

/* Security checks */

function getToken() {
    return window.localStorage.getItem('token');
}

if (getToken() == null && !window.location.href.endsWith('login.html')) {
    window.location.href = 'login.html';
}

function parseToken(token) {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(atob(base64).split('').map(function (c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
}

function checkAdminRights(token) {
    return token.role === 'ADMINISTRATOR';
}

function logout() {
    window.localStorage.removeItem('token');
    window.location.href = 'login.html';
}

function taskStatusIcon(priority) {
    switch (priority) {
        case 'COMMON':
            return 'common.png';
        case 'MEDIUM':
            return 'medium.png';
        case 'URGENT':
            return 'urgent.png';
        default:
            return 'medium.png';
    }
}

function convertFromJavaToJavascriptTime(javaTime) {
    let date = new Date(javaTime);
    let day = date.getDay();
    let month = date.getMonth();
    let hour = date.getHours();
    let minutes = date.getMinutes();
    return `${day}/${month} ${hour}:${minutes}`;
}

/* Pagination */


function writePaginationForSchedules(schedulesPage, size) {
    const totalPages = schedulesPage.totalPages;
    const currentPage = schedulesPage.number;

    if (currentPage === 0) {
        paginationHtml += `<li class="arrow unavailable"><a>PREV.</a></li>`
    } else {
        paginationHtml += `<li class="arrow available"><a
                onclick="getSchedules(${currentPage - 1}, ${size})">PREV.
                </a></li>`
    }

    if (currentPage === totalPages - 1) {
        paginationHtml += `
            <li class="arrow unavailable"><a>NEXT/a></li>`
    } else {
        paginationHtml += `<li class="arrow available"><a
                onclick="getSchedules(${currentPage + 1}, ${size})">NEXT
                </a></li>`
    }

    return paginationHtml;
}