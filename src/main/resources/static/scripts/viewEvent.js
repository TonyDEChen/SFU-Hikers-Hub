var longitude = parseFloat(document.getElementById("lng").value);
var latitude = parseFloat(document.getElementById("lat").value);

function initMap() {
    
    var map = new google.maps.Map(document.getElementById("map"), {
        center: { lat: latitude, lng: longitude },
        zoom: 15,
    });

}