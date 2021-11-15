"use strict";

const baseUrl = 'http://localhost:8080';

const ERRORS = {
    LIMITED_ACCESS_MESSAGE: 'You do not have access',
    USER_BLOCKED_MESSAGE: 'User is blocked.',
    USER_NOT_ACTIVATED: "Your profile is not active yet."
}

const URGENT_TASK_CLASS = 'uk-text-danger';
const NO_SCHEDULES =
    `<div class="uk-alert-success uk-position-center uk-alert">
                                <a class="uk-alert-close"></a>
                                <p>No schedules! You are all done for now!</p>
                                </div>`;
