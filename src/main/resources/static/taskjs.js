// Fetch DOM elements
const taskNameInput = document.getElementById('taskNameInput');
const taskContentInput = document.getElementById('taskContentInput');
const addButton = document.getElementById('addButton');
const dataList = document.getElementById('dataList');

// Get the userName from localStorage
const userName = localStorage.getItem('userName');

if (!userName) {
    alert('User not found. Redirecting to login page.');
    window.location.href = '/'; // Redirect to login page if user is not found
}

// Base API URL for tasks localhost:8080/tasks/test1
const apiUrl = `tasks/${userName}`;
console.log(apiUrl);

// Function to fetch and display all tasks for the user
async function fetchData() {
    try {
        const response = await fetch(apiUrl, {
            method: 'GET'
        });

        if (response.ok) {
            const tasks = await response.json();
            console.log(tasks);
            renderData(tasks);
        } else {
            dataList.innerHTML = '<li>No tasks found for this user.</li>';
        }
    } catch (error) {
        console.error('Error fetching data:', error);
        alert('Failed to fetch tasks. Please try again later.');
    }
}

// Function to render the fetched tasks
function renderData(tasks) {
    dataList.innerHTML = ''; // Clear the list before rendering new tasks
    tasks.forEach(task => {
        const listItem = document.createElement('li');
        listItem.textContent = `${task.title}: ${task.content}`;

        // Add a delete button for each task
        const deleteButton = document.createElement('button');
        deleteButton.textContent = 'Delete';

        deleteButton.onclick = (e) => deleteTask(task.title); // Access ID from data attribute

        listItem.appendChild(deleteButton);
        dataList.appendChild(listItem);
    });
}

// Event listener for the "Save Task" button
addButton.addEventListener('click', async () => {
    const title = taskNameInput.value.trim();
    const content = taskContentInput.value.trim();

    // Validate input
    if (!title || !content) {
        alert('Please enter both task name and content.');
        return;
    }

    const taskData = { title, content };

    try {
        // POST request to save the task
        const response = await fetch(apiUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(taskData)
        });

        if (response.ok) {
            const newTask = await response.json(); // Assuming the server returns the saved task
//            renderData([newTask, ...Array.from(dataList.children).map(li => ({ title: li.content }))]); // Add new task to the list
            fetchData();
            taskNameInput.value = ''; // Clear input fields
            taskContentInput.value = '';

        } else {
            alert('Failed to add task. Please try again.');
        }
    } catch (error) {
        console.error('Error adding task:', error);
        alert('An error occurred while adding the task.');
    }
});

// Function to delete a task
async function deleteTask(title) {
    try {
        const response = await fetch(`${apiUrl}/${title}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            fetchData(); // Refresh the task list
        } else {
            alert('Failed to delete task.');
        }
    } catch (error) {
        console.error('Error deleting task:', error);
        alert('An error occurred while deleting the task.');
    }
}

// Initial fetch of tasks when the page loads
fetchData();
