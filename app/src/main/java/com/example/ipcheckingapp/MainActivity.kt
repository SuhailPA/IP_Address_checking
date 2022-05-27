package com.example.ipcheckingapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var network : String
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        network = getNetwork()
        setContentView(R.layout.activity_main)
        title = "KotlinApp"
        val textView: TextView = findViewById(R.id.getIPAddress)
        val viewModel : MainViewModel by viewModels()
        textView.text = "Your Device IP Address: $network : ${viewModel.getIpAddress() ?: "Not connected to network"}"

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
        network = getNetwork()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getNetwork(): String {
        val connectivityManager =
            applicationContext.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork ?: return "-"
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return "-"
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "WIFI"
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> "ETHERNET"
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "MOBILE NETWORK"
            else -> "Something went wrong.."
        }
    }
}