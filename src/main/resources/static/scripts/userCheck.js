passwordInp = document.getElementById('password');
passwordCheckList = document.querySelectorAll(".list-item");

passwordInp.addEventListener('keyup', function() {
    
    password = passwordInp.value;

    isLengthValid = password.length >= 8 && password.length <= 16;
    hasNumber =    /\d/.test(password);
    hasUpperCase = /[A-Z]/.test(password);
    hasLowerCase = /[a-z]/.test(password);
    hasSymbol =    /[^A-Za-z0-9]/.test(password);

    passwordCheckList[0].classList.toggle('done', isLengthValid);
    passwordCheckList[1].classList.toggle('done', hasNumber);
    passwordCheckList[2].classList.toggle('done', hasUpperCase);
    passwordCheckList[3].classList.toggle('done', hasLowerCase);
    passwordCheckList[4].classList.toggle('done', hasSymbol);

})  

document.querySelector(".submit-button").addEventListener("click", function(event)
{   
    if (!(passwordCheckList[0].classList.contains("done") &&
        passwordCheckList[1].classList.contains("done")   &&
        passwordCheckList[2].classList.contains("done")   &&
        passwordCheckList[3].classList.contains("done")   &&
        passwordCheckList[4].classList.contains("done")) ) {
        event.preventDefault();
        alert("Your password is too weak. Please re-enter a new password.");
    } 
})


