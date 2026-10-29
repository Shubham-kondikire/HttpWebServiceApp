package com.shubham.httpdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

// ---- Student details ----
private const val STUDENT_NAME = "Shubham Shivaji Kondikire"
private const val STUDENT_USN = "25MCAR0102"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                HttpScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HttpScreen() {
    val scope = rememberCoroutineScope()
    var postId by remember { mutableStateOf("1") }
    var status by remember { mutableStateOf("Tap a button to send an HTTP request") }
    var output by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    // Runs a request safely and shows the result / error on screen
    fun run(label: String, call: suspend () -> HttpResult, format: (String) -> String) {
        scope.launch {
            loading = true
            status = "$label ..."
            output = ""
            try {
                val res = call()
                status = "$label -> HTTP ${res.code}"
                output = if (res.code in 200..299) format(res.body) else res.body
            } catch (e: Exception) {
                status = "$label -> FAILED"
                output = "Error: ${e.message}\n(Check internet connection)"
            } finally {
                loading = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Experiment 12 - HTTP Requests")
                        Text(
                            "$STUDENT_NAME | $STUDENT_USN",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(12.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // 1) GET list
            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading,
                onClick = {
                    run("GET /posts?_limit=10", { ApiClient.get("/posts?_limit=10") }) { body ->
                        val arr = JSONArray(body)
                        (0 until arr.length()).joinToString("\n\n") { i ->
                            val o = arr.getJSONObject(i)
                            "#${o.getInt("id")}  ${o.getString("title")}"
                        }
                    }
                }
            ) { Text("GET  /posts  (first 10)") }

            // 2) GET by id
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = postId,
                    onValueChange = { postId = it.filter { c -> c.isDigit() } },
                    label = { Text("Post ID (1-100)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                Button(
                    enabled = !loading && postId.isNotBlank(),
                    onClick = {
                        run("GET /posts/$postId", { ApiClient.get("/posts/$postId") }) { body ->
                            val o = JSONObject(body)
                            "ID: ${o.getInt("id")}\nUser ID: ${o.getInt("userId")}\n\n" +
                                "Title:\n${o.getString("title")}\n\nBody:\n${o.getString("body")}"
                        }
                    }
                ) { Text("GET by ID") }
            }

            // 3) POST
            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading,
                onClick = {
                    val json = JSONObject().apply {
                        put("title", "Experiment 12 by $STUDENT_NAME")
                        put("body", "USN: $STUDENT_USN")
                        put("userId", 1)
                    }.toString()
                    run("POST /posts", { ApiClient.post("/posts", json) }) { body ->
                        JSONObject(body).toString(2)
                    }
                }
            ) { Text("POST  /posts  (send Name + USN)") }

            // Result area
            Card(modifier = Modifier.fillMaxWidth().weight(1f)) {
                Column(
                    modifier = Modifier
                        .padding(12.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        status,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        output,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}
