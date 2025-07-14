package com.jerryokafor.core.database

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

actual val ApplicationDispatcher: kotlinx.coroutines.CoroutineDispatcher
    get() = Dispatchers.Default
