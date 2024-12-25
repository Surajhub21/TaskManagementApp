// Select the necessary elements
const newUserButton = document.querySelector('.new-user');
const existingUserButton = document.querySelector('.existing-user');
const userNameInput = document.getElementById('input_userName');
const passwordInput = document.getElementById('input_password');

// Base URL for the API
const apiUrl = '/user';

// Event listener for the "New User" button
newUserButton.addEventListener('click', async () => {
    const userName = userNameInput.value.trim();
    const password = passwordInput.value.trim();

    if (!userName || !password) {
        alert('Please fill out both username and password fields.');
        return;
    }

    const userData = {
        userName,
        password
    };

    try {
        const response = await fetch('/public', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        });

        if (response.ok) {
            alert('User created successfully!');
        } else {
            alert('Failed to create user.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('An error occurred while creating the user.');
    }
});

// Event listener for the "Existing User" button
existingUserButton.addEventListener('click', async () => {
    const userName = userNameInput.value.trim();

    if (!userName) {
        alert('Please enter a username to check.');
        return;
    }

    try {
        const response = await fetch(`${apiUrl}/${userName}`, {
            method: 'GET'
        });

        if (response.ok) {
            const userExists = await response.json();
            console.log(userExists);
            if (userExists != false) {
            // Store the userName in localStorage
                 localStorage.setItem('userName', userName);
                // Redirect to /taskhtml.html if user exists
                window.location.href = `/taskhtml.html`;
            } else {
                alert('User not found.');
            }
        } else {
            alert('Error fetching user data.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('An error occurred while checking for the user.');
    }
});
