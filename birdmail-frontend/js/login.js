const form = document.getElementById('loginForm');

form.addEventListener('submit', async function(e) {
    e.preventDefault();

    let loginInfo = {};
    loginInfo.email = document.getElementById('email').value;
    loginInfo.password = document.getElementById('password').value;

    console.log(loginInfo);
});