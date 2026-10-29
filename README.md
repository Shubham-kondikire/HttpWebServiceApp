# Experiment 12 – Access Web Services Using HTTP Requests in Android

**Name:** Shubham Shivaji Kondikire
**USN:** 25MCAR0102
**Course:** MCA – Jain (Deemed-to-be University)
**Platform:** Android (Kotlin + Jetpack Compose)

---

## 1. Aim
To develop an Android application that communicates with a web service (REST API) using HTTP **GET** and **POST** requests and displays the JSON response.

## 2. Concept / Technology Behind It

| Concept | Explanation |
|---|---|
| **Web Service / REST API** | A server that exposes data over URLs (endpoints). The app sends a request, the server sends back data, usually in JSON. |
| **HTTP Methods** | **GET** = read data, **POST** = send/create data. |
| **HttpURLConnection** | Android/Java's built-in class used to open a connection to a URL, set the method, headers and read the response. No extra library needed. |
| **JSON Parsing** | The response text is converted to objects using `org.json` (`JSONObject`, `JSONArray`). |
| **INTERNET permission** | `<uses-permission android:name="android.permission.INTERNET"/>` must be in the manifest or every request fails. |
| **Background thread (Coroutines)** | Android blocks network calls on the main thread (`NetworkOnMainThreadException`). Calls run in `Dispatchers.IO` via `withContext` and results update the Compose UI state. |
| **HTTP Status Codes** | 200 = OK, 201 = Created, 404 = Not Found, etc. The app shows the status code with every response. |

**Analogy:** The app is a customer in a restaurant. The URL is the table number, GET is "show me the menu", POST is "place my order", the server is the kitchen, and JSON is the dish served back. The waiter (background thread) goes to the kitchen so the customer (UI) never freezes.

**Web service used:** [JSONPlaceholder](https://jsonplaceholder.typicode.com) – a free fake REST API for testing (no API key needed).

## 3. Scenario Used for Demonstration
A simple "API tester" app with three actions:
1. **GET `/posts?_limit=10`** – fetch and list the first 10 posts.
2. **GET `/posts/{id}`** – fetch one post by the ID typed by the user.
3. **POST `/posts`** – send a new post containing the student's **Name** and **USN**; the server replies with `201 Created` and the new ID.

## 4. Folder / File Structure

```
HttpWebServiceApp/
├── README.md                          -> This documentation
├── .gitignore                         -> Ignores build files and local.properties
├── build.gradle.kts                   -> Root Gradle file (plugin versions)
├── settings.gradle.kts                -> Project name + repositories + modules
├── gradle.properties                  -> Gradle / AndroidX settings
├── gradle/wrapper/
│   └── gradle-wrapper.properties      -> Gradle version used (8.7)
├── docs/screenshots/                  -> Output + test case screenshots
│   ├── output.png
│   ├── testcase1_get_all_posts.png
│   ├── testcase2_get_post_by_id.png
│   └── testcase3_post_name_usn.png
└── app/
    ├── build.gradle.kts               -> App dependencies (Compose only, no network library)
    ├── proguard-rules.pro
    └── src/main/
        ├── AndroidManifest.xml        -> INTERNET permission + launcher activity
        ├── java/com/shubham/httpdemo/
        │   ├── ApiClient.kt           -> HTTP GET / POST helper using HttpURLConnection
        │   └── MainActivity.kt        -> Compose UI, button handlers, JSON parsing
        └── res/values/
            └── strings.xml            -> App name
```

## 5. How to Run
1. Open the project in **Android Studio** (File > Open > `HttpWebServiceApp`), wait for Gradle sync.
2. Run on an emulator or a real phone **with internet access**.
3. Tap the buttons and observe the status code + response.

## 6. Output Screenshot
![Output](docs/screenshots/output.png)

## 7. Test Cases

| # | Test Case | Steps | Expected Result | Screenshot |
|---|---|---|---|---|
| 1 | GET all posts | Tap **GET /posts (first 10)** | Status `HTTP 200`, list of 10 post titles shown | ![TC1](docs/screenshots/testcase1_get_all_posts.png) |
| 2 | GET post by ID | Enter ID `5`, tap **GET by ID** | Status `HTTP 200`, title/body of post 5 shown | ![TC2](docs/screenshots/testcase2_get_post_by_id.png) |
| 3 | POST with Name + USN | Tap **POST /posts** | Status `HTTP 201`, JSON response shows title `Experiment 12 by Shubham Shivaji Kondikire`, body `USN: 25MCAR0102` and new id `101` (app bar also shows Name + USN) | ![TC3](docs/screenshots/testcase3_post_name_usn.png) |

## 8. Result
The application successfully accessed a web service using HTTP GET and POST requests, parsed the JSON response and displayed it in the Android UI.
