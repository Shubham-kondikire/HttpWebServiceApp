
# Experiment 12 – Access Web Services Using HTTP Requests in Android

## Student Details
- **Name:** Shubham Shivaji Kondikire
- **USN:** 25MCAR0102
- **Experiment No.:** 12

## 1. Aim
To develop an Android application that demonstrates how to access web services using HTTP requests. The application performs GET and POST requests to communicate with a web server and display the response.

## 2. Concept
Web services allow Android applications to exchange data with remote servers through the internet.

HTTP provides different request methods:

- **GET Request:** Retrieves data from a server.
- **POST Request:** Sends data to a server.
- **JSON:** A lightweight format used to exchange data between applications and servers.
- **HTTP Response:** Contains a status code and data returned by the server.

## 3. Technologies Used
- Android Studio
- Kotlin
- Jetpack Compose
- HTTP and REST API
- JSONPlaceholder API
- HttpURLConnection
- Kotlin Coroutines

## 4. Scenario
This application demonstrates how an Android application communicates with a web service.

The application uses the JSONPlaceholder API to perform the following operations:

1. Retrieve a list of posts using a GET request.
2. Retrieve a specific post using its ID.
3. Send new post data using a POST request.
4. Display the HTTP response status and response data.

## 5. Features
- User-friendly interface built with Jetpack Compose.
- Fetches a list of posts from a web service.
- Retrieves a particular post by its ID.
- Sends post data using a POST request.
- Displays the server response.
- Performs network operations using Kotlin Coroutines.
- Uses background threads for network communication.
- Includes internet permission in the Android manifest.

## 6. Project Folder Structure

```text
HttpWebServiceApp/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/
│   │       │       └── shubham/
│   │       │           └── httpdemo/
│   │       │               ├── MainActivity.kt
│   │       │               └── ApiClient.kt
│   │       └── AndroidManifest.xml
│   └── build.gradle.kts
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## 7. Important Files

### MainActivity.kt
- Creates the application interface using Jetpack Compose.
- Provides buttons to perform GET and POST requests.
- Displays the response received from the server.

### ApiClient.kt
- Handles HTTP communication with the web service.
- Uses `HttpURLConnection` to establish network connections.
- Sends HTTP requests and reads server responses.
- Uses Kotlin Coroutines to perform network operations in the background.

### AndroidManifest.xml
- Declares the internet permission required for network communication.
- Contains the main application activity configuration.

## 8. API Used

**API Name:** JSONPlaceholder

Website: https://jsonplaceholder.typicode.com/

### GET Request – Fetch Posts
```text
https://jsonplaceholder.typicode.com/posts?_limit=10
```

This request retrieves a list of posts from the server.

### GET Request – Fetch a Specific Post
```text
https://jsonplaceholder.typicode.com/posts/1
```

This request retrieves the post with ID 1.

### POST Request – Create a Post
```text
https://jsonplaceholder.typicode.com/posts
```

The application sends post data containing a title, body, and user ID.

Note: JSONPlaceholder is a testing API. A successful POST request simulates creating a post and does not permanently save the data.

## 9. Test Cases

| Test Case | Operation | Expected Result |
|---|---|---|
| 1 | Fetch all posts | Displays the server response containing posts. |
| 2 | Fetch a specific post | Displays the post corresponding to the requested ID. |
| 3 | Send a POST request | Displays the response returned by the server. |
| 4 | Check HTTP status | Displays the response status code. |
| 5 | Network error | Handles the error and displays the relevant result. |

## 10. Additional Test Cases
- Test the application with an active internet connection.
- Test the application when the internet connection is unavailable.
- Retrieve posts using different post IDs.
- Verify the response received from the API.
- Check the application's behavior when a request fails.

## 11. Output
The application displays buttons to perform HTTP operations and shows the response received from the JSONPlaceholder server.

The expected output includes:
- A list of posts retrieved using GET.
- Details of a specific post.
- A response from the server after a POST request.
- HTTP status information.

## 12. Advantages
- Demonstrates communication between an Android application and a web service.
- Helps understand REST API integration.
- Provides practical experience with HTTP requests.
- Demonstrates asynchronous network operations using Kotlin Coroutines.
- Uses JSON data for exchanging information.

## 13. Conclusion
The Android application successfully demonstrates how to access web services using HTTP requests. It performs GET and POST operations using the JSONPlaceholder API and displays the server responses. This experiment helps in understanding REST APIs, HTTP communication, JSON data, and network programming in Android applications.

## 14. Screenshots
Add screenshots of your application here after running it in Android Studio.

Suggested screenshots:
1. Application home screen.
2. GET request displaying the list of posts.
3. GET request displaying a specific post.
4. POST request displaying the server response.

## Student Information
**Name:** Shubham Shivaji Kondikire  
**USN:** 25MCAR0102  
**Experiment:** 12 – Access Web Services Using HTTP Requests in Android
