var longitude = parseFloat(document.getElementById("lng").value);
var latitude = parseFloat(document.getElementById("lat").value);

var weatherKey = await fetch("/weather/getWeatherKey");
var apiURL = 'https://api.openweathermap.org/data/2.5/weather?lat=' + latitude + '&lon=' + longitude + '&appid=' + weatherKey;

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

