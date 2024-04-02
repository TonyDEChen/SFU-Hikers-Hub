function initAutocomplete() {
    var locationInput = document.getElementById('location');
    var autocomplete = new google.maps.places.Autocomplete(locationInput);
}

// Load the Google Places Autocomplete when the page is loaded
google.maps.event.addDomListener(window, 'load', initAutocomplete);