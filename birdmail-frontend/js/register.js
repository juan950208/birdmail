// todo bien ya
const form = document.getElementById('registerForm');

form.addEventListener('submit', async function(e) {
    e.preventDefault();
    
    let data = {};
    data.firstName = document.getElementById('firstName').value;
    data.lastName = document.getElementById('lastName').value;
    data.username = document.getElementById('email').value;
    data.email = data.username + "@birdmail.com";
    data.password = document.getElementById('password').value;

    let repeatedPassword = document.getElementById('repeatedPassword').value;

    if (repeatedPassword != data.password) {
        alert('The passwords do not match, try again');
        return;
    }

    console.log('Data ready to be sent');
    console.log(data);

    try {
        const response = await fetch('http://localhost:8080/birdmail/users', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        const result = await response.json();

        if (response.ok) {
            alert("INFO: user created successfully")
            form.reset();
        } else {
            console.log("Error on backend: ", result);
            alert('ERROR: error on the server');
        }

    } catch (error) {
        console.error("Network error: ", error);
        alert("ERROR: the connection with the server could not be entablished");
    }

});