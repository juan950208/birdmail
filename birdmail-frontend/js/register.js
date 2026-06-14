// todo bien ya
const form = document.getElementById('registerForm');

form.addEventListener('submit', async function(e) {
    e.preventDefault();

    if (!validateForm()) {
        return;
    }

    let data = {};
    data.firstName = document.getElementById('firstName').value;
    data.lastName = document.getElementById('lastName').value;
    data.email = document.getElementById('email').value;
    data.password = document.getElementById('password').value;

    let repeatedPassword = document.getElementById('repeatedPassword').value;

    if (repeatedPassword != data.password) {
        alert('The passwords do not match, try again');
        return;
    }

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
            alert("INFO: user created successfully");
            console.log(result);
            form.reset();
        } else {
            console.log("ERROR: ", result);
            alert(result.message);
        }

    } catch (error) {
        console.error("ERROR: ", error);
        alert(result.message);
    }
});

function validateForm() {

    let validForm = true;
    const emailRegex = /^[a-zA-Z0-9._]+$/;
    const passwordRegex = /^(?=.*[A-Z])(?=.*\d).{8,}$/
    

    const emailInput = document.getElementById('email');
    const passwordInput = document.getElementById('password');
    const emailError = document.getElementById('email-error');
    const passwordError = document.getElementById('password-error');

    emailError.textContent = "";
    passwordError.textContent = "";

    if (!emailRegex.test(emailInput.value)) {
        emailError.textContent = "Only letters, numbers, dots and underscore are allowed. No spaces.";
        validForm = false;
    }

    if (!passwordRegex.test(passwordInput.value)) {
        passwordError.textContent = "Password must contain at least 8 characters, one uppercase letter and one number.";
        validForm = false;
    }

    return validForm;
}