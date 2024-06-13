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

import cocoapods.Reachability.Reachability
import com.jerryokafor.smshare.core.network.util.NetworkMonitor
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import platform.Foundation.NSLog

class ReachabilityStatusMonitor : NetworkMonitor {
    @OptIn(ExperimentalForeignApi::class)
    private var reachability: Reachability? = null

    @OptIn(ExperimentalForeignApi::class)
    override val isOnline: Flow<Boolean> = callbackFlow {
        dispatch_async(dispatch_get_main_queue()) {
            reachability = Reachability.reachabilityForInternetConnection()

            val reachableCallback = { _: Reachability? ->
                dispatch_async(dispatch_get_main_queue()) {
                    NSLog("Connected")
                    channel.trySend(true)
                }
            }


            val unreachableCallback = { _: Reachability? ->
                dispatch_async(dispatch_get_main_queue()) {
                    NSLog("Disconnected")
                    channel.trySend(false)
                }
            }

            reachability?.reachableBlock = reachableCallback
            reachability?.unreachableBlock = unreachableCallback

            reachability?.startNotifier()

            dispatch_async(dispatch_get_main_queue()) {
                channel.trySend(reachability?.isReachable() ?: false)
                NSLog("Initial reachability: ${reachability?.isReachable()}")
            }
        }


        awaitClose {
            //stop
            reachability?.stopNotifier()
        }

    }.conflate()
}