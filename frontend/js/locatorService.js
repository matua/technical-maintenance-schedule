function getLocation() {
    let options = {
        enableHighAccuracy: true,
        timeout: 5000,
        maximumAge: 0
    };

    // navigator.geolocation.getCurrentPosition(position => {
    //     // Get the positioning coordinates.
    //     document.getElementById('gps_location').innerHTML =
    //         `Your current gps location:</br>
    //     Latitude: ${position.coords.latitude}</br>
    //     Longitude: ${position.coords.longitude}</br>
    //     More or less ${position.coords.accuracy} meters.
    //     `
    //
    //
    // }, function () {
    //     alert('Oops! An error occurred.');
    // }, options);
    navigator.geolocation.getCurrentPosition(
        position => {
            document.getElementById('gps_location').innerHTML =
                `My GPS: ${position.coords.latitude}; ${position.coords.longitude}`
        }, function () {
            alert('Oops! An error occurred.');
        }, options);
}

function getDistanceFromLatLonInMeters(lat1, lon1, lat2, lon2) {
    const R = 6371000; // Radius of the earth in m
    const dLat = deg2rad(lat2 - lat1);  // deg2rad below
    const dLon = deg2rad(lon2 - lon1);
    const a =
        Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
        Math.sin(dLon / 2) * Math.sin(dLon / 2)
    ;
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    const d = R * c; // Distance in m
    return d;

    function deg2rad(deg) {
        return deg * (Math.PI / 180)
    }
}


getLocation();