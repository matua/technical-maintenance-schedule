async function getLocation() {
    let options = {
        enableHighAccuracy: true,
        timeout: 5000,
        maximumAge: 0
    };

    navigator.geolocation.getCurrentPosition(
        position => {
            const latitude = position.coords.latitude;
            const longitude = position.coords.longitude;
            document.getElementById('gps_location').innerHTML =
                `My GPS: ${latitude}, ${longitude}`
            writeLocationToDb(latitude, longitude)
        }, function () {
            latitude = null;
            longitude = null;
            console.log('Oops! An error occurred.');
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
    return R * c;

    function deg2rad(deg) {
        return deg * (Math.PI / 180)
    }
}

async function writeLocationToDb(latitude, longitude) {
    if (checkAdminOrTechRights(parseToken(getToken()))) {
        const url = baseUrl + `/user_locations/${0.3242028450429778}/${32.60611550982706}`;
        await fetch(url, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Authorization': `Bearer ${getToken()}`
            },
        }).catch((err) => {
            console.error(err);
        })
    }
}