// This example creates a 2-pixel-wide red polyline showing the path of
// the first trans-Pacific flight between Oakland, CA, and Brisbane,
// Australia which was made by Charles Kingsford Smith.
function initMap(locations) {
    const map = new google.maps.Map(document.getElementById("map"), {
        zoom: 14,
        center: {lat: 0.31628, lng: 32.58219},
        mapTypeId: "terrain",
    });
    // locations = [
    //     { lat: 0.3242028450429778, lng: 32.60611550982706 },
    //     { lat: 0.3140424, lng: 32.6109124 },
    //     { lat: 0.3143824, lng: 32.5837461 },
    //     { lat: 0.3266208, lng: 32.606579 },
    // ];
    const flightPath = new google.maps.Polyline({
        path: locations,
        geodesic: true,
        strokeColor: "#FF0000",
        strokeOpacity: 1.0,
        strokeWeight: 2,
    });

    flightPath.setMap(map);
}