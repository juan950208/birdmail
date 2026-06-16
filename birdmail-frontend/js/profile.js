function profile() {
    const email = localStorage.getItem("email");

    document.getElementById('email')
    .textContent = email + '@birdmail.com';
} 

profile();