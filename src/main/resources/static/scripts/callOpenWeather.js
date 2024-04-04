var cid;

function processData(data)
{
    var output = document.getElementById("test");
    console.log("testing: " + data);
    output.innerHTML = '<p>Temperature: ' + data.main.temp + '</p>';
}

async function getData()
{

    var weatherKey = await fetch("/weather/getWeatherKey")
        .then(response => response.text())
        .then(data=> {
            return data;
        })

        var longitude = parseFloat(document.getElementById("lng").value);
        var latitude = parseFloat(document.getElementById("lat").value);
    
        var apiURL = 'https://api.openweathermap.org/data/2.5/weather?lat=' + latitude + '&lon=' + longitude + '&appid=' + weatherKey;
        console.log(apiURL);

        var jsonDATA;

        fetch(apiURL)
        .then(response => {
            if(!response.ok) {
                throw new Error("api call");
            }
            return response.json();
            })
            .then(data => {
                console.log(data);
                var output = document.getElementById("test");
                //console.log(output.innerHTML);
                //console.log("testing: " + data);
                //output.innerHTML = '<p>city id: ' + data.id + '</p>';
                cid = data.id;
                loadWidget(cid);

            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
    });
}

window.addEventListener("DOMContentLoaded", (event) => {
    console.log("page is fully loaded");
    getData();
});
    

    




