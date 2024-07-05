/*
 * MIT License
 *
 * Copyright (c) 2024 SM Share Project
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.jerryokafor.smshare.core.network.util.util

import com.jerryokafor.smshare.core.network.util.NetworkMonitor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import java.io.IOException
import java.net.MalformedURLException
import java.net.NetworkInterface
import java.net.URL
import java.net.URLConnection

class NetworkConnectivityObserver : NetworkMonitor {
    override val isOnline: Flow<Boolean> = callbackFlow {
        while (true) {
//            val isConnected = checkNetworkConnectivity()
            val isConnected = netIsAvailable()
            channel.trySend(isConnected)
            delay(5000) // Adjust the delay according to your needs
        }

//        awaitClose {}
    }.conflate()

    private fun checkNetworkConnectivity(): Boolean {
        return try {
            val interfaces = NetworkInterface.getNetworkInterfaces()
            while (interfaces.hasMoreElements()) {
                val networkInterface = interfaces.nextElement()
                if (networkInterface.isUp && !networkInterface.isLoopback) {
                    return true
                }
            }
            false
        } catch (e: Exception) {
            false
        }
    }

    private fun netIsAvailable(): Boolean {
        try {
            val url: URL = URL("http://www.google.com")
            val conn: URLConnection = url.openConnection()
            conn.connect()
            conn.getInputStream().close()
            return true
        } catch (e: MalformedURLException) {
            throw RuntimeException(e)
        } catch (e: IOException) {
            return false
        }
    }
}
