function initAutocomplete() {
    var locationInput = document.getElementById('location');
    var autocomplete = new google.maps.places.Autocomplete(locationInput);

    // Listen for the 'place_changed' event
    autocomplete.addListener('place_changed', function() {
        var place = autocomplete.getPlace();
        if (!place.geometry) {
            console.log("Place details not available for input: '" + place.name + "'");
            return;
        }

        // Extract latitude and longitude
        var latitude = place.geometry.location.lat();
        var longitude = place.geometry.location.lng();

        // Update hidden input field with coordinates
        // document.getElementById('location_coordinates').value = latitude + ',' + longitude;
        document.getElementById('longitude').value = longitude;
        document.getElementById('latitude').value = latitude;

        getWeatherData(latitude, longitude);

        
    });
}

async function getWeatherData(latitude, longitude) 
{
    var weatherKey = await fetch("/weather/getWeatherKey");

        var apiURL = 'https://history.openweathermap.org/data/2.5/aggregated/year?lat=' + latitude + '&lon=' + longitude + '&appid=' + weatherKey;
        fetch(apiURL)
            .then(response => {
                if(!response.ok) {
                    throw new Error("api call");
                }
                return response.json();
            })
            .then(data => {
                console.log(data);

            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });
}

// Load the Google Places Autocomplete when the page is loaded
google.maps.event.addDomListener(window, 'load', initAutocomplete);