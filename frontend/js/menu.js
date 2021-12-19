document.write(`
<script src="js/common.js"></script>

<div id="offcanvas-nav-primary" uk-offcanvas="overlay: true">
    <div class="uk-offcanvas-bar uk-flex uk-flex-column">

        <ul class="uk-nav uk-nav-primary uk-nav-center uk-margin-auto-vertical">
            <li class="uk-parent">
            </li>
            <li><a href="tasks.html"><span class="uk-margin-small-right" uk-icon="icon: table"></span>All Tasks</a></li>
            <li><a href="schedules.html"><span class="uk-margin-small-right" uk-icon="icon: table"></span>All Schedules</a></li>
            <li><a href="users.html"><span class="uk-margin-small-right" uk-icon="icon: table"></span>All Users</a></li>
            <li class="uk-nav-divider"></li>
            <li><a><span id="logoutButton" class="uk-margin-small-right" uk-icon="icon: sign-out"></span>Log out</a></li>
        </ul>

    </div>
</div>`);