const form = document.getElementById('loginForm');

form.addEventListener('submit', async function(e) {
    e.preventDefault();

    let loginInfo = {};
    loginInfo.email = document.getElementById('email').value;
    loginInfo.password = document.getElementById('password').value;
    loginError = document.getElementById('login-error');
    loginError.textContent = "";

    try {
        const request = await fetch('http://localhost:8080/birdmail/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(loginInfo)
        });

        const result = await request.json();

        if (request.ok) {
            localStorage.token = result.token;
            localStorage.email = loginInfo.email;
            window.location.href = 'dashboard.html';
        } else {
            loginError.textContent = result.message;
            console.log('ERROR ' + result.message);
        }

    } catch (error) {
        console.log('ERROR (captured in catch): ' + error)
    }
});