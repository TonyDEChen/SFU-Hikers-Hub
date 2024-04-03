// function initMap(){
//     const map = new google.maps.Map(document.getElementById("map"), {
//         center:{
//             lat: 0,
//             lng: 0
//         },
//         zoom: 13,
//         mapTypeControl: false,
//     });
// }

// // window.initMap = initMap;
// google.maps.event.addDomListener(window, 'load', initMap);


// Get the event location from the HTML
// var eventLocation = document.querySelector(".important").textContent;
var longitude = parseFloat(document.getElementById("lng").value);
var latitude = parseFloat(document.getElementById("lat").value);

// Initialize the map
function initMap() {
    
    // Create a map object and specify the DOM element for display
    var map = new google.maps.Map(document.getElementById("map"), {
        center: { lat: latitude, lng: longitude },
        zoom: 15, // Default zoom level
    });

    // // Use the Geocoding API to get the coordinates of the event location
    // var geocoder = new google.maps.Geocoder();
    // geocoder.geocode({ address: eventLocation }, function (results, status) {
    //     if (status === google.maps.GeocoderStatus.OK) {
    //         // Set the map center to the event location
    //         map.setCenter(results[0].geometry.location);
            
    //         // Place a marker at the event location
    //         var marker = new google.maps.Marker({
    //             map: map,
    //             position: results[0].geometry.location,
    //         });
    //     } else {
    //         console.error("Geocode was not successful for the following reason: " + status);
    //     }
    // });
}


// document.addEventListener("DOMContentLoaded", function() {
//     // Create a map centered on the provided longitude and latitude
//     var mapOptions = {
//         center: { lat: latitude, lng: longitude },
//         zoom: 15
//     };
//     var map = new google.maps.Map(document.getElementById('map'), mapOptions);
// });


// function initMap(){}

// $(() => {
//     initMap = function() {
//         var map = new google.maps.Map(document.getElementById("map"), {
//             center: { lat: latitude, lng: longitude },
//             zoom: 15,
//         });
//     }
//   })
