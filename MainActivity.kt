package com.bmt.ipfinder

import android.app.Activity
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.os.Handler
import android.os.Looper
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.concurrent.Executors

class MainActivity : Activity() {
    private lateinit var web: WebView
    private val executor = Executors.newSingleThreadExecutor()
    private val mainHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        web = WebView(this)
        web.settings.javaScriptEnabled = true
        web.settings.domStorageEnabled = true
        web.webChromeClient = WebChromeClient()
        web.webViewClient = WebViewClient()
        web.addJavascriptInterface(AndroidBridge(), "AndroidBridge")
        setContentView(web)
        web.loadUrl("file:///android_asset/index.html")
    }

    inner class AndroidBridge {
        @JavascriptInterface
        fun lookup(ip: String, callbackId: String) {
            executor.execute {
                try {
                    val targetIp = if (ip.isBlank()) {
                        val ipJson = request("https://api.ipify.org?format=json")
                        JSONObject(ipJson).getString("ip")
                    } else ip.trim()

                    val encoded = URLEncoder.encode(targetIp, "UTF-8")
                    val result = request("https://nid.proo.one/ip-info.php?ip=$encoded")
                    val json = JSONObject(result)
                    mainHandler.post {
                        web.evaluateJavascript(
                            "window.onNativeLookup(${JSONObject.quote(callbackId)}, ${JSONObject.quote(json.toString())})",
                            null
                        )
                    }
                } catch (e: Exception) {
                    val error = JSONObject().put("success", false)
                        .put("error", e.message ?: "Network error").toString()
                    mainHandler.post {
                        web.evaluateJavascript(
                            "window.onNativeLookup(${JSONObject.quote(callbackId)}, ${JSONObject.quote(error)})",
                            null
                        )
                    }
                }
            }
        }
    }

    private fun request(address: String): String {
        val connection = (URL(address).openConnection() as HttpURLConnection)
        connection.requestMethod = "GET"
        connection.connectTimeout = 15000
        connection.readTimeout = 15000
        connection.setRequestProperty("Accept", "application/json")
        return try {
            val stream = if (connection.responseCode in 200..299) connection.inputStream else connection.errorStream
            stream.bufferedReader().use { it.readText() }
        } finally {
            connection.disconnect()
        }
    }

    override fun onDestroy() {
        web.destroy()
        executor.shutdown()
        super.onDestroy()
    }
}
