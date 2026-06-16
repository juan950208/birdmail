document.addEventListener("DOMContentLoaded", function() {
    if (validateSession()) {
        loadUserInbox();
    }
});

function loadUserInbox() {
    const email = localStorage.getItem("email");

    document.getElementById('logged-user-email')
    .textContent = email;
} 

function logout() {
    localStorage.clear();
    window.location.href = 'login.html';
}

function validateSession() {
    const token = localStorage.getItem('token');

    if (!token || token.split('.').length !== 3) {  
        window.location.replace('index.html');
        return false;
    }

    try {
        const playLoad = JSON.parse(window.atob(token.split('.')[1]));
        const now = Math.floor(Date.now() / 1000);

        if (now >= playLoad.exp) {
            localStorage.removeItem('token');
            window.location.replace('index.html');
            return false;
        }

    } catch (error) {
        console.log("ERROR (catch validate session): " + error);
        return false;
    }

    return true;
}