"use strict";

let usersHtml;
let paginationHtml;
let logout_button = document.getElementById('logout_button');

async function getTasks(page = 0, size = 10) {
    loadAnimation('users');
    usersHtml = '';
    paginationHtml = '';
    usersHtml = '';
    const url = baseUrl + `/admin/users/${page}/${size}`;

    if (checkAdminRights(parseToken(getToken()))) {
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
            .then(usersPage => {
                writeUsersToTable(usersPage);
                writePaginationForUsers(usersPage, size);
            })
            .catch((err) => {
                console.error(err);
                usersHtml =
                    NO_USERS;
            })
        unloadAnimation(
            'users');
        document.getElementById(
            'users')
            .innerHTML =
            usersHtml;
        document
            .getElementsByClassName(
                'pagination')[0]
            .innerHTML =
            paginationHtml;
    } else {
        usersHtml = `<div class="uk-alert-danger uk-position-center uk-alert">
                                <a class="uk-alert-close"></a>
                                <p>Not authorized!</p>`;
    }
    document.getElementById('users').innerHTML = usersHtml;


    function writeUsersToTable(p) {
        const tasksTableHeaders =
            `<a href="" uk-icon="icon: plus" class="uk-padding-large">Add new    </a>
            <div class="uk-overflow-auto">
            <table class="uk-table uk-table-hover uk-table-middle uk-table-divider">
                <thead>
                    <tr>
                        <th class="uk-table-shrink">Id</th>
                        <th class="uk-table-expand">First Name</th>
                        <th class="uk-width-small">Last Name</th>
                        <th class="uk-width-small">Role</th>
                        <th class="uk-width-small">Active</th>
                        <th class="uk-width-small">On duty</th>
                        <th class="uk-width-small">Field hours</th>
                        <th class="uk-width-small">Base latitude</th>
                        <th class="uk-width-small">Base longitude</th>
                        <th class="uk-width-small"></th>
                        <th class="uk-width-small"></th>
                    </tr>
                </thead>
                <tbody>`
        const tasksTableFooter =
            `</tbody>
       </table>`
        p.content.forEach(
            user => {
                usersHtml +=
                    `<tr>
                        <td class="uk-table-link">
                            <a class="uk-link-reset">${user.id}</a>
                        </td>
                        <td class="uk-text-truncate">${user.firstName}</td>
                        <td class="uk-text-truncate">${user.lastName}</td>
                        <td class="uk-text-truncate">${user.role}</td>
                        <td class="uk-text-truncate">${user.active}</td>
                        <td class="uk-text-truncate">${user.onDuty}</td>
                        <td class="uk-text-truncate">${user.fieldHours}</td>
                        <td class="uk-text-truncate">${user.baseLatitude}</td>
                        <td class="uk-text-truncate">${user.baseLongitude}</td>
                         <td class="uk-text-truncate">    
                        <a class="uk-icon-link" uk-icon="minus-circle" onclick="deleteTask(${user.id})"></a></td>
                    </tr>`
            });
        usersHtml = tasksTableHeaders + usersHtml + tasksTableFooter;
        return usersHtml;
    }
}

logout_button.addEventListener('click', logout);

getTasks();
getLocation();

async function deleteUser(id) {
    duDialog(null, 'This action cannot be undone, proceed?', {
        buttons: duDialog.OK_CANCEL,
        okText: 'Delete User',
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
