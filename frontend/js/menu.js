document.write(`

<div id="offcanvas-nav-primary" uk-offcanvas="overlay: true">
    <div class="uk-offcanvas-bar uk-flex uk-flex-column">

        <ul class="uk-nav uk-nav-primary uk-nav-center uk-margin-auto-vertical">
            <li class="uk-active"><a href="#">Active</a></li>
            <li class="uk-parent">
<!--                <ul class="uk-nav-sub">-->
<!--                    <li><a href="#">Sub item</a></li>-->
<!--                    <li><a href="#">Sub item</a></li>-->
<!--                </ul>-->
            </li>
            <li><a href="tasks.html"><span class="uk-margin-small-right" uk-icon="icon: table"></span>All Tasks</a></li>
            <li><a href="schedules.html"><span class="uk-margin-small-right" uk-icon="icon: table"></span>All Schedules</a></li>
            <li class="uk-nav-divider"></li>
            <li><a href="login.html"><span class="uk-margin-small-right" uk-icon="icon: sign-out"></span>Log out</a></li>
        </ul>

    </div>
</div>

<div id="offcanvas-nav" uk-offcanvas="overlay: true">
    <div class="uk-offcanvas-bar">

        <ul class="uk-nav uk-nav-default">
            <li class="uk-active"><a href="#">Active</a></li>
            <li class="uk-parent">
                <a href="#">Parent</a>
                <ul class="uk-nav-sub">
                    <li><a href="#">Sub item</a></li>
                    <li><a href="#">Sub item</a></li>
                </ul>
            </li>
            <li class="uk-nav-header">Header</li>
            <li><a href="#"><span class="uk-margin-small-right" uk-icon="icon: table"></span> Item</a></li>
            <li><a href="#"><span class="uk-margin-small-right" uk-icon="icon: thumbnails"></span> Item</a></li>
            <li class="uk-nav-divider"></li>
            <li id="logout_button" class="uk-button uk-button-secondary uk-width-1-1">>Log out</a></li>
        </ul>
    </div>
</div>`);