package com.shubham.httpdemo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

/** Result of one HTTP call: status code + response body. */
data class HttpResult(val code: Int, val body: String)

/**
 * Small HTTP helper built on HttpURLConnection (no extra libraries needed).
 * Network calls are NOT allowed on the main thread, so everything runs on Dispatchers.IO.
 */
object ApiClient {
    const val BASE_URL = "https://jsonplaceholder.typicode.com"

    /** HTTP GET request */
    suspend fun get(path: String): HttpResult = withContext(Dispatchers.IO) {
        val conn = URL("$BASE_URL$path").openConnection() as HttpURLConnection
        try {
            conn.requestMethod = "GET"
            conn.connectTimeout = 10_000
            conn.readTimeout = 10_000
            conn.setRequestProperty("Accept", "application/json")
            readResponse(conn)
        } finally {
            conn.disconnect()
        }
    }

    /** HTTP POST request with a JSON body */
    suspend fun post(path: String, json: String): HttpResult = withContext(Dispatchers.IO) {
        val conn = URL("$BASE_URL$path").openConnection() as HttpURLConnection
        try {
            conn.requestMethod = "POST"
            conn.connectTimeout = 10_000
            conn.readTimeout = 10_000
            conn.doOutput = true
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
            conn.outputStream.use { it.write(json.toByteArray(Charsets.UTF_8)) }
            readResponse(conn)
        } finally {
            conn.disconnect()
        }
    }

    private fun readResponse(conn: HttpURLConnection): HttpResult {
        val code = conn.responseCode
        val stream = if (code in 200..299) conn.inputStream else conn.errorStream
        val body = stream?.bufferedReader()?.use { it.readText() } ?: ""
        return HttpResult(code, body)
    }
}
