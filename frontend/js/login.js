"use strict";

let login_form = document.getElementById('login_form');
let login_button = document.getElementById('login_button');

async function login(event) {
    event.preventDefault();

    const form = new FormData(login_form);
    let loginData = fromFormDataToJson(form);

    await fetch(`${baseUrl}/auth`, {
        method: 'POST',
        headers: {"Content-type": "application/json; charset=UTF-8"},
        body: JSON.stringify(loginData)
    })
        .then(response => response.json())
        .then((json) => {
            if (json.token != null) {
                if ((parseToken(json.token)).active === "true") {
                    localStorage.setItem('token', json.token);
                    window.location.href = 'tasks.html';
                } else {
                    document.getElementById("user_message").innerHTML = ERRORS.USER_NOT_ACTIVATED;
                }
            } else {
                window.location.reload();
            }
        }).catch(function (error) {
            console.log(error);
        });
}

if (login_form != null) {
    login_button.addEventListener('click', login);
}

