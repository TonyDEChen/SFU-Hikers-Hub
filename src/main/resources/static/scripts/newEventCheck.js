
document.getElementById("create".addEventListener("click", function(event)
{
    var title = document.getElementById("title");
    var location = document.getElementById("location");
    var time = document.getElementById("time");
    var body = document.getElementById("description");

    if(!(title && title.value && location && location.value && time && time.value && body && body.value))
    {
        event.preventDefault();
        alert("Please fill out all the fields");
    }
}));

