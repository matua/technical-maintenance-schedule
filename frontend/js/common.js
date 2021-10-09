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
}

// function writePaginationForProducts(productsPage, size) {
//     const totalPages = productsPage.totalPages;
//     const currentPage = productsPage.number;
//
//     if (currentPage === 0) {
//         paginationHtml += `<li class="arrow unavailable"><a>&laquo;</a></li>`
//     } else {
//         paginationHtml += `<li class="arrow available"><a
//                 onclick="getProducts(${currentPage - 1}, ${size})">&laquo;
//                 </a></li>`
//     }
//
//     for (let page = 1; page <= totalPages; page++) {
//         if (page - 1 === currentPage) {
//             paginationHtml += `<li class="current"><a>${page}</a></li>`;
//         } else {
//             paginationHtml += `<li><a onclick="getProducts(${page - 1}, ${size})">${page}</a></li>`;
//         }
//     }
//
//     if (currentPage === totalPages - 1) {
//         paginationHtml += `
//             <li class="arrow unavailable"><a>&#187;</a></li>`
//     } else {
//         paginationHtml += `<li class="arrow available"><a
//                 onclick="getProducts(${currentPage + 1}, ${size})">&#187;
//                 </a></li>`
//     }
//
//     return paginationHtml;
// }
//
// function writePaginationForUsers(usersPage, size) {
//     const totalPages = usersPage.totalPages;
//     const currentPage = usersPage.number;
//
//     if (currentPage === 0) {
//         paginationHtml += `<li class="arrow unavailable"><a>&laquo;</a></li>`
//     } else {
//         paginationHtml += `<li class="arrow available"><a
//                 onclick="getUsers(${currentPage - 1}, ${size})">&laquo;
//                 </a></li>`
//     }
//
//     for (let page = 1; page <= totalPages; page++) {
//         if (page - 1 === currentPage) {
//             paginationHtml += `<li class="current"><a>${page}</a></li>`;
//         } else {
//             paginationHtml += `<li><a onclick="getUsers(${page - 1}, ${size})">${page}</a></li>`;
//         }
//     }
//
//     if (currentPage === totalPages - 1) {
//         paginationHtml += `
//             <li class="arrow unavailable"><a>&#187;</a></li>`
//     } else {
//         paginationHtml += `<li class="arrow available"><a
//                 onclick="getUsers(${currentPage + 1}, ${size})">&#187;
//                 </a></li>`
//     }
//
//     return paginationHtml;
// }

function darkenScreen() {
    let fade = document.getElementById('blockDiv');
    fade.classList.remove('hide');
}

function clearScreen() {
    let fade = document.getElementById('blockDiv');
    fade.classList.add('hide');
}

function getBrowserLanguage() {
    return navigator.language || navigator.userLanguage;
}

async function getCurrentUsername() {
    await fetch(baseUrl + `/username`, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Authorization': `Bearer ${getToken()}`
        },
    })
        .then((response) => {
            return response.json();
        })
}