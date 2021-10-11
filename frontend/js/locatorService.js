function getLocation() {
    let options = {
        enableHighAccuracy: true,
        timeout: 5000,
        maximumAge: 0
    };

    navigator.geolocation.getCurrentPosition(function (position) {
        // Get the positioning coordinates.
        document.getElementById('location').innerHTML =
            `Your current gps location:</br>
        Latitude: ${position.coords.latitude}</br>
        Longitude: ${position.coords.longitude}</br>
        More or less ${position.coords.accuracy} meters.
        `


    }, function () {
        alert('Oops! An error occurred.');
    }, options);
}

function getDistance(source, destination) {
    return google.maps.geometry.spherical.computeDistanceBetween(
        new google.maps.LatLng(source.lat, source.lng),
        new google.maps.LatLng(destination.lat, destination.lng)
    );
}

getLocation();