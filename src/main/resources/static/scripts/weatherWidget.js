function loadWidget(cid)
{
    console.log("city id: " + cid);
    if(cid != 0)
    {
        window.myWidgetParam ? window.myWidgetParam : window.myWidgetParam = [];  
        window.myWidgetParam.push({id: 11,cityid: cid,appid: 'd24bf298c7865a33da767b557c21f9a9',units: 'metric',containerid: 'openweathermap-widget-11',  });  
        (function() {var script = document.createElement('script');script.async = true;script.charset = "utf-8";
        script.src = "//openweathermap.org/themes/openweathermap/assets/vendor/owm/js/weather-widget-generator.js";
        var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(script, s);  })();
    }
    else
    {
        document.getElementById("errortext").innerHTML = "No Weather Data available for this location";
    }
    
}